package io.github.hellovie.snapvids.infrastructure.service.upload.repository.impl;

import io.github.hellovie.snapvids.common.module.upload.UploadCacheKey;
import io.github.hellovie.snapvids.infrastructure.cache.CacheService;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.ChunkFileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.FileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.ChunkFile;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;
import io.github.hellovie.snapvids.infrastructure.service.upload.entity.ChunkFileMetadata;
import io.github.hellovie.snapvids.infrastructure.service.upload.entity.FileMetadata;
import io.github.hellovie.snapvids.infrastructure.service.upload.repository.StorageRepository;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文件存储仓储默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("storageRepository")
public class DefaultStorageRepository implements StorageRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultStorageRepository.class);

    @Resource(name = "fileDao")
    private FileDao fileDao;

    @Resource(name = "chunkFileDao")
    private ChunkFileDao chunkFileDao;

    @Resource(name = "redisCacheService")
    private CacheService cacheService;

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#findByFileKeyAndUserId(FileKey, Id)
     */
    @Override
    public FileMetadata findByFileKeyAndUserId(FileKey fileKey, Id userId) {
        if (fileKey == null || userId == null) {
            return null;
        }
        Optional<File> optional = fileDao.findByFileKeyAndCreatedById(fileKey.getValue(), userId.getValue());
        return optional.map(this::toFileMetadata).orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#findByFileIdAndCurrentNum(Id, ChunkNumber)
     */
    @Override
    public ChunkFileMetadata findByFileIdAndCurrentNum(Id fileId, ChunkNumber currentNum) {
        if (fileId == null || currentNum == null) {
            return null;
        }

        Optional<ChunkFile> optional = chunkFileDao.findByFileIdAndCurrentNum(fileId.getValue(), currentNum.getValue());
        return optional.map(this::toChunkFileMetadata).orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#saveChunkFileMetadata(ChunkFileMetadata)
     */
    @Override
    public ChunkFileMetadata saveChunkFileMetadata(ChunkFileMetadata chunkFileMetadata) {
        ChunkFile chunkFile = new ChunkFile();
        if (chunkFileMetadata.getId() != null) {
            chunkFile.setId(chunkFileMetadata.getId().getValue());
        }

        chunkFile.setFileId(chunkFileMetadata.getFileId().getValue())
                .setCurrentNum(chunkFileMetadata.getCurrentNum().getValue())
                .setCurrentSize(chunkFileMetadata.getCurrentSize().getValue())
                .setChunkSize(chunkFileMetadata.getChunkSize().getValue())
                .setTotalSize(chunkFileMetadata.getTotalSize().getValue())
                .setTotalChunks(chunkFileMetadata.getTotalChunks().getValue());

        return toChunkFileMetadata(chunkFileDao.save(chunkFile));
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#findChunkFileMetadataByFileId(Id)
     */
    @Override
    public List<ChunkFileMetadata> findChunkFileMetadataByFileId(Id fileId) {
        if (fileId == null) {
            return Collections.emptyList();
        }

        List<ChunkFile> chunkFiles = chunkFileDao.findAllByFileId(fileId.getValue());
        return chunkFiles.stream().map(this::toChunkFileMetadata).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#deleteAllChunkByFileId(Id)
     */
    @Override
    public long deleteAllChunkByFileId(Id fileId) {
        if (fileId == null) {
            return 0;
        }

        return chunkFileDao.deleteByFileId(fileId.getValue());
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#findById(Id)
     */
    @Override
    public FileMetadata findById(Id fileId) {
        if (fileId == null) {
            return null;
        }

        Optional<File> optional = fileDao.findById(fileId.getValue());
        return optional.map(this::toFileMetadata).orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#saveTempUrl(Id, ValueString, long)
     */
    @Override
    public void saveTempUrl(Id fileId, ValueString tempUrl, long expiredInSeconds) {
        if (fileId == null || tempUrl == null || expiredInSeconds <= 0) {
            return;
        }

        String key = UploadCacheKey.TEMP_URL + fileId.getValue();
        boolean isCacheSuccess = cacheService.setValue(key, tempUrl.getValue(), expiredInSeconds, TimeUnit.SECONDS);
        if (!isCacheSuccess) {
            LOG.warn("[缓存文件临时访问URL失败]>>> 缓存Key={}，缓存值={}，缓存时间={}，时间单位={}",
                    key, tempUrl.getValue(), expiredInSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#findTempUrlById(Id)
     */
    @Override
    public String findTempUrlById(Id fileId) {
        String key = UploadCacheKey.TEMP_URL + fileId.getValue();
        String url = cacheService.getString(key);
        if (StringUtils.isBlank(url)) {
            return "";
        }

        return url;
    }

    /**
     * {@link File} to {@link FileMetadata}.
     *
     * @param file {@link File}
     * @return {@link FileMetadata}
     */
    private FileMetadata toFileMetadata(File file) {
        if (file == null) {
            return null;
        }

        return new FileMetadata(
                new Id(file.getId()),
                new Filename(file.getOriginalName()),
                new Filename(file.getStorageName()),
                new FileKey(file.getFileKey()),
                new FilePath(file.getPath()),
                file.getExt(),
                new FileSize(file.getSize()),
                file.getType()
        );
    }

    /**
     * {@link ChunkFile} to {@link ChunkFileMetadata}.
     *
     * @param chunkFile {@link ChunkFile}
     * @return {@link ChunkFileMetadata}
     */
    private ChunkFileMetadata toChunkFileMetadata(ChunkFile chunkFile) {
        if (chunkFile == null) {
            return null;
        }

        return new ChunkFileMetadata(
                new Id(chunkFile.getId()),
                new Id(chunkFile.getFileId()),
                new ChunkNumber(chunkFile.getCurrentNum()),
                new FileSize(chunkFile.getCurrentSize()),
                new FileSize(chunkFile.getChunkSize()),
                new FileSize(chunkFile.getTotalSize()),
                new ChunkTotal(chunkFile.getTotalChunks())
        );
    }
}
