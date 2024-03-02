package io.github.hellovie.snapvids.application.upload.service.impl;

import io.github.hellovie.snapvids.application.upload.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.upload.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.upload.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.upload.event.InitUploadCommand;
import io.github.hellovie.snapvids.application.upload.service.FileUploadService;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.domain.file.config.FilePathConfig;
import io.github.hellovie.snapvids.domain.file.entity.FileInfo;
import io.github.hellovie.snapvids.domain.file.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.file.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.domain.file.service.FileService;
import io.github.hellovie.snapvids.domain.file.state.FileUpdateStateEvent;
import io.github.hellovie.snapvids.domain.storage.event.CheckUploadedCommand;
import io.github.hellovie.snapvids.domain.storage.event.GenUploadTokenCommand;
import io.github.hellovie.snapvids.domain.storage.factory.StorageFactory;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileVisibility;
import io.github.hellovie.snapvids.types.file.FilePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 默认文件上传服务实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("fileUploadService")
public class DefaultFileUploadService implements FileUploadService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileUploadService.class);

    @Resource(name = "fileService")
    private FileService fileService;

    @Resource(name = "storageFactory")
    private StorageFactory storageFactory;

    /**
     * {@inheritDoc}
     *
     * @see FileUploadService#initUpload(InitUploadCommand)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadTokenDTO initUpload(InitUploadCommand command) {
        CreateFileInfoCommand cmd = new CreateFileInfoCommand(
                command.getOriginalName(),
                command.getIdentifier(),
                new FilePath(FilePathConfig.DEFAULT),
                command.getExt(),
                command.getSize(),
                command.getExt().getType(),
                StorageFactory.DEFAULT_STORAGE,
                FileVisibility.ALL
        );
        FileInfo fileInfo = fileService.create(cmd);
        StorageService storageService = storageFactory.getDefaultStorageService();
        UploadToken uploadToken = storageService.generateUploadToken(new GenUploadTokenCommand(
                fileInfo.getId(), fileInfo.getIdentifier()
        ));
        return UploadTokenDTO.Convertor.toUploadTokenDTO(uploadToken);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileUploadService#finishUpload(FinishUploadCommand)
     */
    @Override
    public FileInfoDTO finishUpload(FinishUploadCommand command) {
        StorageService storageService = storageFactory.getDefaultStorageService();
        boolean isUploaded = storageService.checkUploaded(new CheckUploadedCommand(command.getFileIdentifier()));
        if (!isUploaded) {
            throw new DataException(FileExceptionType.FILE_HAS_NOT_YET_BEEN_UPLOADED);
        }
        UpdateFileStateCommand cmd = new UpdateFileStateCommand(
                command.getFileIdentifier(),
                FileState.UPLOADING,
                FileUpdateStateEvent.COMPLETE_THE_UPLOAD
        );
        FileInfo fileInfo = fileService.updateState(cmd);
        return FileInfoDTO.Convertor.toFileInfoDTO(fileInfo, storageService);
    }
}
