package io.github.hellovie.snapvids.domain.storage.service.impl;

import io.github.hellovie.snapvids.common.exception.business.AuthException;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.service.TokenService;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.storage.entity.ChunkFileMetadata;
import io.github.hellovie.snapvids.domain.storage.entity.FileMetadata;
import io.github.hellovie.snapvids.domain.storage.event.MergePartEvent;
import io.github.hellovie.snapvids.domain.storage.event.SingleUploadEvent;
import io.github.hellovie.snapvids.domain.storage.event.UploadPartEvent;
import io.github.hellovie.snapvids.domain.storage.event.UploadProgressQuery;
import io.github.hellovie.snapvids.domain.storage.repository.StorageRepository;
import io.github.hellovie.snapvids.domain.storage.service.UploadService;
import io.github.hellovie.snapvids.domain.storage.vo.UploadProgressVO;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.infrastructure.properties.JwtProperties;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.hellovie.snapvids.domain.config.LocalUploadConfig.*;

/**
 * 本地上传服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("localUploadService")
public class LocalUploadService implements UploadService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalUploadService.class);

    /**
     * JWT Token 配置类
     */
    @Resource(name = "jwtProperties")
    private JwtProperties jwtProperties;

    /**
     * JWT Token 服务
     */
    @Resource(name = "jwtTokenService")
    private TokenService jwtTokenService;

    @Resource(name = "storageRepository")
    private StorageRepository repository;

    /**
     * {@inheritDoc}
     *
     * @see UploadService#createToken(Id, FileKey)
     */
    @Override
    public UploadToken createToken(Id fileId, FileKey fileHash) throws UtilException {
        long nowTimestamp = new Date().getTime() / 1000;
        long expiredTimestamp = nowTimestamp + TOKEN_EXPIRED_IN_SECONDS;
        try {
            HashMap<String, String> payload = new HashMap<>(3);
            payload.put(TOKEN_KEY_USER_ID, Long.toString(ContextHolder.getUserOrElseThrow().getId().getValue()));
            payload.put(TOKEN_KEY_FILE_ID, Long.toString(fileId.getValue()));
            payload.put(TOKEN_KEY_FILE_HASH, fileHash.getValue());
            String jwtToken = jwtTokenService.create(payload, TOKEN_EXPIRED_IN_SECONDS, jwtProperties.getSecret());
            UploadToken token = new UploadToken(
                    new Id(fileId.getValue()),
                    new FileKey(fileHash.getValue()),
                    ValueString.buildOrElseThrowByMessage(jwtToken, "上传令牌生成失败"),
                    nowTimestamp,
                    expiredTimestamp
            );
            LOG.info("[生成上传令牌成功]>>> 令牌={}", token);
            return token;
        } catch (Exception ex) {
            throw new UtilException(FileExceptionType.UPLOAD_TOKEN_GENERATE_FAILED, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadService#checkToken(UploadToken)
     */
    @Override
    public void checkToken(UploadToken token) throws AuthException {
        long now = new Date().getTime() / 1000;
        if (token == null || token.getExpiredTime() <= now) {
            LOG.info("[上传令牌已过期]>>> 令牌={}", token);
            throw new AuthException(FileExceptionType.UPLOAD_TOKEN_HAS_EXPIRED);
        }
        if (token.getToken() == null || token.getFileId() == null || token.getFileKey() == null) {
            LOG.info("[上传令牌参数不完整]>>> 令牌={}", token);
            throw new AuthException(FileExceptionType.INVALID_UPLOAD_TOKEN);
        }

        // 解析令牌
        SysUser curUser = ContextHolder.getUserOrElseThrow();
        try {
            Map<String, String> payload = jwtTokenService.getPayload(jwtProperties.getSecret(), token.getToken().getValue());
            long userId = Long.parseLong(payload.get(TOKEN_KEY_USER_ID));
            long fileId = Long.parseLong(payload.get(TOKEN_KEY_FILE_ID));
            String fileHash = payload.get(TOKEN_KEY_FILE_HASH);
            if (userId != curUser.getId().getValue() || fileId != token.getFileId().getValue() ||
                    !token.getFileKey().getValue().equals(fileHash)) {
                LOG.info("[上传令牌参数与JWT载荷的数据不符]>>> 上传令牌=[{}, {}, {}]，载荷=[{}, {}, {}]",
                        curUser.getId(), token.getFileId(), token.getFileKey(), userId, fileId, fileHash);
                throw new AuthException(FileExceptionType.INVALID_UPLOAD_TOKEN);
            }
        } catch (Exception ex) {
            LOG.info("[JWT解析失败]>>> {}", ex.getMessage());
            throw new AuthException(FileExceptionType.INVALID_UPLOAD_TOKEN, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadService#singleUpload(SingleUploadEvent)
     */
    @Override
    public void singleUpload(SingleUploadEvent event) throws DataException {
        UploadToken token = new UploadToken(event.getFileId(), event.getFileKey(), event.getToken(),
                event.getStartTime(), event.getExpiredTime());
        checkToken(token);

        MultipartFile uploadFile = event.getFile();
        if (uploadFile.getSize() > SINGLE_UPLOAD_MAX_FILE_SIZE) {
            throw new DataException(FileExceptionType.UPLOAD_EXCEED_SIZE_LIMIT_FILE);
        }

        SysUser curUser = ContextHolder.getUserOrElseThrow();
        FileMetadata fileMetadata = repository.findByFileKeyAndUserId(event.getFileKey(), curUser.getId());
        if (fileMetadata == null) {
            LOG.info("[找不到文件，文件上传失败]>>> 文件哈希={}，用户ID={}", event.getFileKey(), curUser.getId());
            throw new DataException(FileExceptionType.FILE_NOT_FOUNT);
        }

        // 存储前校验文件完整性
        String fileHash = FileKey.calculateHash(uploadFile);
        if (!event.getHash().getValue().equals(fileHash)) {
            LOG.warn("[文件数据被篡改]>>> 文件ID={}，传入文件哈希={}，实际文件哈希={}",
                    event.getFileId(), event.getHash(), fileHash);
            throw new DataException(FileExceptionType.UPLOAD_FILE_FAILED);
        }

        // 文件保存到本地
        saveToDisk(
                new Filename(fileMetadata.getStorageName() + "." + fileMetadata.getExt().name().toLowerCase()),
                fileMetadata.getPath(),
                uploadFile
        );
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadService#uploadPart(UploadPartEvent)
     */
    @Override
    public void uploadPart(UploadPartEvent event) {
        UploadToken token = new UploadToken(event.getFileId(), event.getFileKey(), event.getToken(),
                event.getStartTime(), event.getExpiredTime());
        checkToken(token);

        MultipartFile uploadFile = event.getFile();
        if (uploadFile.getSize() > SINGLE_UPLOAD_MAX_FILE_SIZE) {
            throw new DataException(FileExceptionType.UPLOAD_EXCEED_SIZE_LIMIT_FILE);
        }

        SysUser curUser = ContextHolder.getUserOrElseThrow();
        FileMetadata fileMetadata = repository.findByFileKeyAndUserId(event.getFileKey(), curUser.getId());
        if (fileMetadata == null) {
            LOG.info("[找不到文件，分片上传失败]>>> 文件哈希={}，用户ID={}", event.getFileKey(), curUser.getId());
            throw new DataException(FileExceptionType.FILE_NOT_FOUNT);
        }

        // 分片存在直接秒传
        ChunkFileMetadata chunkFileMetadata = repository.findByFileIdAndCurrentNum(event.getFileId(), event.getCurrentNum());
        if (chunkFileMetadata != null) {
            return;
        }

        // 分片不存在，需要保存分片记录
        chunkFileMetadata = new ChunkFileMetadata(
                event.getFileId(),
                event.getCurrentNum(),
                event.getCurrentSize(),
                event.getChunkSize(),
                event.getTotalSize(),
                event.getTotalChunks()
        );

        // 存储前校验文件完整性
        String fileHash = FileKey.calculateHash(uploadFile);
        if (!event.getHash().getValue().equals(fileHash)) {
            LOG.warn("[文件数据被篡改]>>> 文件ID={}，传入文件哈希={}，实际文件哈希={}",
                    event.getFileId(), event.getHash(), fileHash);
            throw new DataException(FileExceptionType.UPLOAD_FILE_FAILED);
        }

        // 文件保存到本地
        saveToDisk(
                new Filename(fileMetadata.getStorageName() + "." + fileMetadata.getExt().name().toLowerCase() +
                        "-" + chunkFileMetadata.getCurrentNum().getValue()),
                fileMetadata.getPath(),
                uploadFile
        );

        // 保存分片记录
        ChunkFileMetadata saved = repository.saveChunkFileMetadata(chunkFileMetadata);
        LOG.info("[保存分片记录成功]>>> 文件ID={}，分片ID={}，分片编号={}",
                saved.getFileId(), saved.getId(), saved.getCurrentNum());
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadService#getUploadProgress(UploadProgressQuery)
     */
    @Override
    public UploadProgressVO getUploadProgress(UploadProgressQuery query) throws DataException {
        UploadToken token = new UploadToken(query.getFileId(), query.getFileKey(), query.getToken(),
                query.getStartTime(), query.getExpiredTime());
        checkToken(token);

        SysUser curUser = ContextHolder.getUserOrElseThrow();
        FileMetadata fileMetadata = repository.findByFileKeyAndUserId(query.getFileKey(), curUser.getId());
        if (fileMetadata == null) {
            LOG.info("[找不到文件，查询分片上传进度失败]>>> 文件哈希={}，用户ID={}", query.getFileKey(), curUser.getId());
            throw new DataException(FileExceptionType.FILE_NOT_FOUNT);
        }

        // 查询文件是否存在磁盘
        if (whetherInDisk(fileMetadata)) {
            return new UploadProgressVO(Boolean.TRUE, Boolean.TRUE, Collections.emptyList());
        }

        List<ChunkFileMetadata> chunkFileMetadataList = repository.findChunkFileMetadataByFileId(query.getFileId());
        // 文件不存在磁盘，同时也没有分片记录，说明从未上传分片
        if (chunkFileMetadataList.isEmpty()) {
            return new UploadProgressVO(Boolean.FALSE, Boolean.FALSE, Collections.emptyList());
        }

        Integer totalChunks = chunkFileMetadataList.get(0).getTotalChunks().getValue();
        // 文件不存在磁盘，但是分片数量完整，说明就差合并
        if (totalChunks == chunkFileMetadataList.size()) {
            return new UploadProgressVO(Boolean.FALSE, Boolean.TRUE, Collections.emptyList());
        }

        // 文件不存在磁盘，但是分片不完整，说明可以断点续传
        List<Integer> allNumbers = IntStream.rangeClosed(1, totalChunks)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> uploadNumbers = chunkFileMetadataList.stream()
                .map(chunk -> chunk.getCurrentNum().getValue())
                .collect(Collectors.toList());
        allNumbers.removeAll(uploadNumbers);
        return new UploadProgressVO(Boolean.FALSE, Boolean.TRUE,
                allNumbers.stream().map(ChunkNumber::new).collect(Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadService#mergePart(MergePartEvent)
     */
    @Override
    public void mergePart(MergePartEvent event) throws DataException {
        UploadToken token = new UploadToken(event.getFileId(), event.getFileKey(), event.getToken(),
                event.getStartTime(), event.getExpiredTime());
        checkToken(token);

        SysUser curUser = ContextHolder.getUserOrElseThrow();
        FileMetadata fileMetadata = repository.findByFileKeyAndUserId(event.getFileKey(), curUser.getId());
        if (fileMetadata == null) {
            LOG.info("[找不到文件，合并文件失败]>>> 文件哈希={}，用户ID={}", event.getFileKey(), curUser.getId());
            throw new DataException(FileExceptionType.FILE_NOT_FOUNT);
        }

        List<ChunkFileMetadata> chunkFileMetadataList = repository.findChunkFileMetadataByFileId(event.getFileId());
        // 没有任何分片
        if (chunkFileMetadataList.isEmpty()) {
            LOG.info("[没有任何分片文件，无法合并]>>> 文件ID={}", event.getFileId());
            throw new DataException(FileExceptionType.CHUNK_FILE_NOT_FOUND);
        }

        Integer totalChunks = chunkFileMetadataList.get(0).getTotalChunks().getValue();
        // 分片文件不完整
        if (totalChunks != chunkFileMetadataList.size()) {
            LOG.info("[缺少分片，无法合并]>>> 文件ID={}", event.getFileId());
            throw new DataException(FileExceptionType.MISSING_CHUNK_FILE);
        }

        // 删除数据库中的分片文件信息（无论合并是否成功都需要删除分片信息）
        long count = repository.deleteAllChunkByFileId(event.getFileId());
        if (count != totalChunks) {
            LOG.warn("[数据库分片数量不等于实际分片数量]>>> 数据库分片数量={}，实际分片数量={}", count, totalChunks);
            throw new DataException(FileExceptionType.DB_CHUNK_FILE_DATA_EXCEPTION,
                    FileExceptionType.MERGE_CHUNK_FILE_FAILED.getMessage());
        }

        // 分片完整，开始合并
        boolean isMergeSuccess = mergeFile(
                fileMetadata.getFileKey(),
                fileMetadata.getPath(),
                new Filename(fileMetadata.getStorageName() + "." + fileMetadata.getExt().name().toLowerCase()),
                fileMetadata.getSize(),
                chunkFileMetadataList.get(0).getTotalChunks()
        );
        if (!isMergeSuccess) {
            LOG.info("[合并文件失败]>>> 文件ID={}", event.getFileId());
            throw new DataException(FileExceptionType.MERGE_CHUNK_FILE_FAILED);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadService#checkUploaded(FileKey)
     */
    @Override
    public boolean checkUploaded(FileKey fileHash) {
        SysUser curUser = ContextHolder.getUserOrElseThrow();
        FileMetadata fileMetadata = repository.findByFileKeyAndUserId(fileHash, curUser.getId());
        if (fileMetadata == null) {
            LOG.info("[找不到文件，文件未上传成功]>>> 文件哈希={}，用户ID={}", fileHash, curUser.getId());
            return false;
        }

        return whetherInDisk(fileMetadata);
    }

    /**
     * 文件保存到本地。
     *
     * @param filename      保存在磁盘的文件名（包含后缀）
     * @param path          保存在磁盘的路径（根路径下的相对路径）（例如：/default）
     * @param multipartFile 保存在磁盘的文件
     * @throws UtilException 文件保存失败抛出
     */
    private void saveToDisk(Filename filename, FilePath path, MultipartFile multipartFile) throws UtilException {
        final String filePath = FILE_ROOT_PATH + path.getValue();
        File folder = new File(filePath);
        if (!folder.exists()) {
            boolean existDirs = folder.mkdirs();
            if (!existDirs) {
                LOG.error("[创建文件夹失败]>>> 文件夹路径={}", filePath);
                throw new UtilException(FileExceptionType.CREATE_FOLDER_FAILED);
            }
        }

        File file = new File(folder, filename.getValue());
        try {
            multipartFile.transferTo(file);
        } catch (IOException ex) {
            LOG.error("[无法将文件保存到磁盘]>>> 保存路径={}", filePath + "/" + filename.getValue());
            throw new UtilException(FileExceptionType.FILE_SAVE_TO_DISK_FAILED, ex);
        }
    }

    /**
     * 判断文件是否存在磁盘中。
     *
     * @param fileMetadata 文件元数据
     * @return true：存在磁盘
     */
    private boolean whetherInDisk(FileMetadata fileMetadata) {
        if (fileMetadata == null) {
            return false;
        }

        String path = FILE_ROOT_PATH + fileMetadata.getPath().getValue() + "/" +
                fileMetadata.getStorageName() + "." + fileMetadata.getExt().name().toLowerCase();
        File file = new File(path);
        if (file.exists()) {
            LOG.info("[文件存在磁盘中]>>> 文件全路径={}", path);
            return true;
        }
        LOG.info("[文件不存在磁盘中]>>> 文件全路径={}", path);
        return false;
    }

    /**
     * 合并文件。
     *
     * @param hash       数据库文件哈希
     * @param path       数据库文件路径
     * @param filename   数据库文件名（包含后缀）
     * @param fileSize   数据库文件大小
     * @param chunkTotal 数据库文件分片总数
     * @return true：合并成功
     */
    private boolean mergeFile(FileKey hash, FilePath path, Filename filename, FileSize fileSize, ChunkTotal chunkTotal) {
        final String filePath = FILE_ROOT_PATH + path.getValue();
        File folder = new File(filePath);
        if (!folder.exists()) {
            LOG.error("[文件夹不存在，合并文件失败]>>> 文件夹路径={}", filePath);
            return false;
        }

        // 创建一个空文件，用于合并
        File mergedFile = new File(folder, filename.getValue());
        List<File> inputFiles = new ArrayList<>(chunkTotal.getValue());
        for (int i = 1; i <= chunkTotal.getValue(); i++) {
            File file = new File(folder, filename.getValue() + "-" + i);
            if (!file.exists()) {
                LOG.error("[找不到分片文件，合并文件失败]>>> 文件名={}，文件路径={}", file.getName(), file.getPath());
                return false;
            }
            inputFiles.add(file);
        }

        // 正式合并文件
        try (OutputStream fos = FileUtils.openOutputStream(mergedFile)) {
            for (File inputFile : inputFiles) {
                try (InputStream fis = FileUtils.openInputStream(inputFile)) {
                    IOUtils.copy(fis, fos);
                }
            }
            LOG.info("[文件合并成功]>>> 文件名={}，文件路径={}", mergedFile.getName(), mergedFile.getPath());

            // 删除分片文件
            for (File inputFile : inputFiles) {
                FileUtils.forceDelete(inputFile);
            }
            LOG.info("[文件分片已经全部删除]>>> 文件名={}，文件路径={}，分片数量={}",
                    mergedFile.getName(), mergedFile.getPath(), inputFiles.size());
        } catch (IOException ex) {
            // 可能出现磁盘中无分片文件，数据库中却有分片信息的情况.
            LOG.error("[正式合并文件失败]>>> 异常消息={}", ex.getMessage(), ex);
            return false;
        }

        // 文件合并成功，校验文件完整性
        try {
            String mergedFileHash = FileKey.calculateHash(mergedFile);
            if (mergedFile.length() != fileSize.getValue() || StringUtils.isBlank(mergedFileHash) ||
                    !mergedFileHash.equals(hash.getValue())) {
                LOG.error("[文件合并失败]>>> 数据库文件大小={}，磁盘文件大小={}，数据库文件哈希={}，磁盘文件哈希={}",
                        fileSize, mergedFile.length(), hash, mergedFileHash);
                // 文件合并失败，将文件删除
                FileUtils.forceDelete(mergedFile);
                LOG.error("[文件合并失败，删除文件成功]>>> 数据库文件大小={}，磁盘文件大小={}，数据库文件哈希={}，磁盘文件哈希={}",
                        fileSize, mergedFile.length(), hash, mergedFileHash);
                return false;
            }
        } catch (IOException ex) {
            LOG.error("[校验文件完整性失败]>>> 异常消息={}", ex.getMessage(), ex);
            return false;
        }
        return true;
    }
}
