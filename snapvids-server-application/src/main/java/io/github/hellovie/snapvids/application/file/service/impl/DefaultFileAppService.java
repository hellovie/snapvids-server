package io.github.hellovie.snapvids.application.file.service.impl;

import io.github.hellovie.snapvids.application.file.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.file.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.file.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.file.event.InitUploadCommand;
import io.github.hellovie.snapvids.application.file.service.FileAppService;
import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.common.enums.FileVisibility;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileInfo;
import io.github.hellovie.snapvids.domain.fileInfo.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.fileInfo.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.domain.fileInfo.service.FileInfoService;
import io.github.hellovie.snapvids.domain.fileInfo.state.FileUpdateStateEvent;
import io.github.hellovie.snapvids.domain.storage.event.CheckUploadedCommand;
import io.github.hellovie.snapvids.domain.storage.event.GenUploadTokenCommand;
import io.github.hellovie.snapvids.domain.storage.event.GetTempUrlQuery;
import io.github.hellovie.snapvids.domain.storage.event.GetUrlQuery;
import io.github.hellovie.snapvids.domain.storage.factory.StorageFactory;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 文件应用服务默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("fileAppService")
public class DefaultFileAppService implements FileAppService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileAppService.class);

    @Resource(name = "fileInfoService")
    private FileInfoService fileInfoService;

    @Resource(name = "storageFactory")
    private StorageFactory storageFactory;

    /**
     * {@inheritDoc}
     *
     * @see FileAppService#initUpload(InitUploadCommand)
     */
    @Override
    public UploadTokenDTO initUpload(InitUploadCommand command) {
        CreateFileInfoCommand cmd = new CreateFileInfoCommand(
                command.getOriginalName(),
                command.getFileKey(),
                command.getPath(),
                command.getExt(),
                command.getSize(),
                command.getExt().getType(),
                StorageFactory.DEFAULT_STORAGE,
                FileVisibility.ALL
        );
        FileInfo fileInfo = fileInfoService.create(cmd);
        StorageService storageService = storageFactory.getDefaultStorageService();
        UploadToken uploadToken = storageService.generateUploadToken(new GenUploadTokenCommand(
                fileInfo.getId(), fileInfo.getFileKey()
        ));
        return UploadTokenDTO.Convertor.toUploadTokenDTO(uploadToken);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileAppService#finishUpload(FinishUploadCommand)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileInfoDTO finishUpload(FinishUploadCommand command) {
        UpdateFileStateCommand cmd = new UpdateFileStateCommand(
                command.getFileKey(),
                FileState.UPLOADING,
                FileUpdateStateEvent.COMPLETE_THE_UPLOAD
        );
        FileInfo fileInfo = fileInfoService.updateState(cmd);
        StorageService storageService = storageFactory.getStorageService(fileInfo.getStorage());
        if (storageService == null) {
            throw new DataException(FileExceptionType.FILE_STORAGE_SERVICE_IS_NOT_SUPPORTED);
        }

        boolean isUploaded = storageService.checkUploaded(new CheckUploadedCommand(command.getFileKey()));
        if (!isUploaded) {
            throw new DataException(FileExceptionType.FILE_HAS_NOT_YET_BEEN_UPLOADED);
        }
        return FileInfoDTO.Convertor.toFileInfoDTO(fileInfo, storageService, true);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileAppService#getForeverUrlByFileId(Id)
     */
    @Override
    public ValueString getForeverUrlByFileId(Id fileId) {
        FileInfo fileInfo = fileInfoService.getValidFileInfoById(fileId);
        if (fileInfo == null) {
            throw new DataException(FileExceptionType.FILE_NOT_FOUND);
        }

        StorageService storageService = storageFactory.getStorageService(fileInfo.getStorage());
        if (storageService == null) {
            throw new DataException(FileExceptionType.FILE_STORAGE_SERVICE_IS_NOT_SUPPORTED);
        }

        return storageService.getUrl(new GetUrlQuery(fileId));
    }

    /**
     * {@inheritDoc}
     *
     * @see FileAppService#getTempUrlByFileId(Id)
     */
    @Override
    public ValueString getTempUrlByFileId(Id fileId) {
        FileInfo fileInfo = fileInfoService.getValidFileInfoById(fileId);
        if (fileInfo == null) {
            throw new DataException(FileExceptionType.FILE_NOT_FOUND);
        }

        StorageService storageService = storageFactory.getStorageService(fileInfo.getStorage());
        if (storageService == null) {
            throw new DataException(FileExceptionType.FILE_STORAGE_SERVICE_IS_NOT_SUPPORTED);
        }

        return storageService.getTempUrl(new GetTempUrlQuery(fileId));
    }
}
