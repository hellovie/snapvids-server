package io.github.hellovie.snapvids.domain.storage.service.impl;

import io.github.hellovie.snapvids.common.enums.FileStorage;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.domain.storage.event.GetTempUrlQuery;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.domain.storage.annotation.StorageServiceMark;
import io.github.hellovie.snapvids.domain.storage.event.CheckUploadedCommand;
import io.github.hellovie.snapvids.domain.storage.event.GenUploadTokenCommand;
import io.github.hellovie.snapvids.domain.storage.event.GetUrlQuery;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.infrastructure.service.upload.impl.LocalUploadService;
import io.github.hellovie.snapvids.infrastructure.service.upload.vo.LocalUploadToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.*;

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
        if (command == null) {
            throw new UtilException(GEN_UPLOAD_TOKEN_FAILED);
        }

        try {
            Id curUserId = ContextHolder.getUserOrElseThrow().getId();
            LocalUploadToken token = uploadService.createToken(curUserId, command.getFileId(), command.getFileKey());
            return UploadToken.Convertor.toUploadToken(token);
        } catch (Exception ex) {
            throw new UtilException(GEN_UPLOAD_TOKEN_FAILED, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageService#getUrl(GetUrlQuery)
     */
    @Override
    public ValueString getUrl(GetUrlQuery query) throws UtilException {
        if (query == null) {
            throw new UtilException(GET_FILE_ACCESS_URL_FAILED);
        }

        try {
            Id curUserId = ContextHolder.getUserOrElseThrow().getId();
            return uploadService.getUrl(curUserId, query.getFileKey());
        } catch (Exception ex) {
            LOG.error("[获取文件访问路径异常]>>> 方法入参={}", query);
            throw new UtilException(GET_FILE_ACCESS_URL_FAILED, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageService#getTempUrl(GetTempUrlQuery)
     */
    @Override
    public ValueString getTempUrl(GetTempUrlQuery query) throws UtilException {
        if (query == null) {
            throw new UtilException(GET_FILE_TEMP_ACCESS_URL_FAILED);
        }

        try {
            Id curUserId = ContextHolder.getUserOrElseThrow().getId();
            return uploadService.getTempUrl(curUserId, query.getFileKey());
        } catch (Exception ex) {
            LOG.error("[获取文件临时访问路径异常]>>> 方法入参={}", query);
            throw new UtilException(GET_FILE_TEMP_ACCESS_URL_FAILED, ex);
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

        Id curUserId = ContextHolder.getUserOrElseThrow().getId();
        return uploadService.checkUploaded(curUserId, command.getFileKey());
    }
}
