package io.github.hellovie.snapvids.application.upload.service.impl;

import io.github.hellovie.snapvids.application.upload.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.upload.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.upload.event.*;
import io.github.hellovie.snapvids.application.upload.service.FileUploadService;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.domain.file.config.FilePathConfig;
import io.github.hellovie.snapvids.domain.file.entity.FileInfo;
import io.github.hellovie.snapvids.domain.file.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.file.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.domain.file.service.FileService;
import io.github.hellovie.snapvids.domain.file.state.FileUpdateStateEvent;
import io.github.hellovie.snapvids.domain.storage.event.*;
import io.github.hellovie.snapvids.domain.storage.factory.StorageFactory;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.domain.storage.service.UploadService;
import io.github.hellovie.snapvids.domain.storage.vo.UploadProgressVO;
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

    @Resource(name = "localUploadService")
    private UploadService uploadService;

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
                command.getFileKey(),
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
                fileInfo.getId(), fileInfo.getFileKey()
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
        boolean isUploaded = storageService.checkUploaded(new CheckUploadedCommand(command.getFileKey()));
        if (!isUploaded) {
            throw new DataException(FileExceptionType.FILE_HAS_NOT_YET_BEEN_UPLOADED);
        }
        UpdateFileStateCommand cmd = new UpdateFileStateCommand(
                command.getFileKey(),
                FileState.UPLOADING,
                FileUpdateStateEvent.COMPLETE_THE_UPLOAD
        );
        FileInfo fileInfo = fileService.updateState(cmd);
        return FileInfoDTO.Convertor.toFileInfoDTO(fileInfo, storageService);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileUploadService#upload(UploadEvent)
     */
    @Override
    public void upload(UploadEvent event) {
        SingleUploadEvent cmd = new SingleUploadEvent(
                event.getFileId(),
                event.getFileKey(),
                event.getToken(),
                event.getStartTime(),
                event.getExpiredTime(),
                event.getCurrentFileHash(),
                event.getFile()
        );
        uploadService.singleUpload(cmd);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileUploadService#uploadWithChunks(UploadChunksEvent)
     */
    @Override
    public void uploadWithChunks(UploadChunksEvent event) {
        UploadPartEvent uploadPartEvent = new UploadPartEvent(
                event.getFileId(),
                event.getFileKey(),
                event.getToken(),
                event.getStartTime(),
                event.getExpiredTime(),
                event.getCurrentNum(),
                event.getCurrentSize(),
                event.getChunkSize(),
                event.getTotalSize(),
                event.getTotalChunks(),
                event.getChunkHash(),
                event.getFile()
        );
        uploadService.uploadPart(uploadPartEvent);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileUploadService#getUploadProgress(UploadChunksProgressQuery)
     */
    @Override
    public UploadChunksProgressDTO getUploadProgress(UploadChunksProgressQuery query) {
        UploadProgressQuery progressQuery = new UploadProgressQuery(
                query.getFileId(),
                query.getFileKey(),
                query.getToken(),
                query.getStartTime(),
                query.getExpiredTime()
        );
        UploadProgressVO uploadProgress = uploadService.getUploadProgress(progressQuery);
        return UploadChunksProgressDTO.Convertor.toUploadChunksProgressDTO(uploadProgress);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileUploadService#mergeChunks(MergeChunksEvent)
     */
    @Override
    public void mergeChunks(MergeChunksEvent event) {
        MergePartEvent mergePartEvent = new MergePartEvent(
                event.getFileId(),
                event.getFileKey(),
                event.getToken(),
                event.getStartTime(),
                event.getExpiredTime()
        );
        uploadService.mergePart(mergePartEvent);
    }
}
