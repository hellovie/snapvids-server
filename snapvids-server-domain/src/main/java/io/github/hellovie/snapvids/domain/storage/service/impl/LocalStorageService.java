package io.github.hellovie.snapvids.domain.storage.service.impl;

import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.service.TokenService;
import io.github.hellovie.snapvids.common.util.ProjectUtils;
import io.github.hellovie.snapvids.domain.storage.annotation.StorageServiceMark;
import io.github.hellovie.snapvids.domain.storage.entity.FileMetadata;
import io.github.hellovie.snapvids.domain.storage.event.CheckUploadedCommand;
import io.github.hellovie.snapvids.domain.storage.event.GenUploadTokenCommand;
import io.github.hellovie.snapvids.domain.storage.event.GetUrlQuery;
import io.github.hellovie.snapvids.domain.storage.repository.StorageRepository;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage;
import io.github.hellovie.snapvids.infrastructure.properties.JwtProperties;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FileIdentifier;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.Filename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * 本地存储服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("localStorageService")
@StorageServiceMark(type = FileStorage.LOCAL)
public class LocalStorageService implements StorageService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalStorageService.class);

    @Value("${server.port}")
    private int port;

    private static final String PROTOCOL = "http";

    @Resource(name = "storageRepository")
    private StorageRepository storageRepository;

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

    /**
     * 令牌有效时间（单位：秒）
     */
    private static final long TOKEN_EXPIRED_IN_SECONDS = 30 * 60;

    /**
     * {@inheritDoc}
     *
     * @see StorageService#generateUploadToken(GenUploadTokenCommand)
     */
    @Override
    public UploadToken generateUploadToken(GenUploadTokenCommand command) throws UtilException {
        long nowTimestamp = new Date().getTime() / 1000;
        long expiredTimestamp = nowTimestamp + TOKEN_EXPIRED_IN_SECONDS;
        HashMap<String, String> payload = new HashMap<>(0);
        String jwtToken = jwtTokenService.create(payload, TOKEN_EXPIRED_IN_SECONDS, jwtProperties.getSecret());
        UploadToken token = new UploadToken(
                new Id(command.getFileId().getValue()),
                new FileIdentifier(command.getFileIdentifier().getValue()),
                ValueString.buildOrElseThrowByMessage(jwtToken, "上传令牌生成失败"),
                nowTimestamp,
                expiredTimestamp
        );
        LOG.info("[Generate upload token]>>> token={}", token);
        return token;
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageService#getUrl(GetUrlQuery)
     */
    @Override
    public ValueString getUrl(GetUrlQuery query) throws UtilException {
        if (query == null) {
            throw new UtilException(FileExceptionType.GET_FILE_ACCESS_URL_FAILED);
        }

        try {
            FileMetadata fileMetadata = storageRepository.findByIdentifier(query.getIdentifier());
            FilePath path = fileMetadata.getPath();
            Filename storageName = fileMetadata.getStorageName();
            FileExt ext = fileMetadata.getExt();
            String url = PROTOCOL + "://" + ProjectUtils.getProjectIp() + ":" + port +
                    path.getValue() + "/" + storageName.getValue() + "." + ext.name().toLowerCase();
            return ValueString.buildOrElseThrowByMessage(url, "获取文件访问路径失败");
        } catch (Exception ex) {
            LOG.error("[Failed to obtain the file access url]>>> query={}", query);
            throw new UtilException(FileExceptionType.GET_FILE_ACCESS_URL_FAILED, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageService#checkUploaded(CheckUploadedCommand)
     */
    @Override
    public boolean checkUploaded(CheckUploadedCommand command) {
        // Todo：需要查询文件是否以上传到本地
        // 文件存储路径
        try {
            FileMetadata fileMetadata = storageRepository.findByIdentifier(command.getIdentifier());
            if (fileMetadata == null) {
                return false;
            }
            String path = fileMetadata.getPath().getValue() + "/" + fileMetadata.getStorageName().getValue() +
                    "." + fileMetadata.getExt().name().toLowerCase();
            LOG.info("[Obtained the local storage path of the file]>>> path={}", path);
            return true;
        } catch (Exception ex) {
            LOG.error("[Check uploaded exception]>>> command={}, message={}", command, ex.getMessage(), ex);
            return false;
        }
    }
}
