package io.github.hellovie.snapvids.domain.storage.service.impl;

import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
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
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.Filename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
     * 本地上传服务
     */
    @Resource(name = "localUploadService")
    private LocalUploadService uploadService;

    /**
     * {@inheritDoc}
     *
     * @see StorageService#generateUploadToken(GenUploadTokenCommand)
     */
    @Override
    public UploadToken generateUploadToken(GenUploadTokenCommand command) throws UtilException {
        return uploadService.createToken(command.getFileId(), command.getFileKey());
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
            FileMetadata fileMetadata = storageRepository.findByFileKeyAndUserId(query.getFileKey(),
                    query.getCreatedById());
            FilePath path = fileMetadata.getPath();
            Filename storageName = fileMetadata.getStorageName();
            FileExt ext = fileMetadata.getExt();
            String url = PROTOCOL + "://" + ProjectUtils.getProjectIp() + ":" + port +
                    path.getValue() + "/" + storageName.getValue() + "." + ext.name().toLowerCase();
            return ValueString.buildOrElseThrowByMessage(url, "获取文件访问路径失败");
        } catch (Exception ex) {
            LOG.error("[获取文件访问路径异常]>>> 方法入参={}", query);
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
        if (command == null || command.getFileKey() == null) {
            return false;
        }

        return uploadService.checkUploaded(command.getFileKey());
    }
}
