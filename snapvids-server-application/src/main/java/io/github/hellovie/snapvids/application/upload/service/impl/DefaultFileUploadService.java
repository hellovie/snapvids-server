package io.github.hellovie.snapvids.application.upload.service.impl;

import io.github.hellovie.snapvids.application.upload.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.upload.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.upload.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.upload.event.InitUploadCommand;
import io.github.hellovie.snapvids.application.upload.service.FileUploadService;
import io.github.hellovie.snapvids.domain.file.config.FilePathConfig;
import io.github.hellovie.snapvids.domain.file.entity.FileInfo;
import io.github.hellovie.snapvids.domain.file.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.file.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.domain.file.service.FileService;
import io.github.hellovie.snapvids.domain.storage.factory.StorageFactory;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileVisibility;
import io.github.hellovie.snapvids.types.file.FilePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        UploadToken uploadToken = storageService.generateUploadToken(fileInfo.getId(), fileInfo.getIdentifier());
        return UploadTokenDTO.Convertor.toUploadTokenDTO(uploadToken);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileUploadService#finishUpload(FinishUploadCommand)
     */
    @Override
    public FileInfoDTO finishUpload(FinishUploadCommand command) {
        UpdateFileStateCommand cmd = new UpdateFileStateCommand(command.getId(), FileState.UPLOADED);
        FileInfo fileInfo = fileService.updateState(cmd);
        return FileInfoDTO.Convertor.toFileInfoDTO(fileInfo);
    }
}
