package io.github.hellovie.snapvids.application.upload.service.impl;

import io.github.hellovie.snapvids.application.upload.event.*;
import io.github.hellovie.snapvids.application.upload.service.UploadAppService;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.infrastructure.service.upload.UploadService;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.MergePartEvent;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.SingleUploadEvent;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.UploadPartEvent;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.UploadProgressQuery;
import io.github.hellovie.snapvids.infrastructure.service.upload.vo.UploadProgressVO;
import io.github.hellovie.snapvids.types.common.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 本地上传应用服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("localUploadAppService")
public class LocalUploadAppService implements UploadAppService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalUploadAppService.class);

    @Resource(name = "localUploadService")
    private UploadService uploadService;

    /**
     * {@inheritDoc}
     *
     * @see UploadAppService#upload(UploadEvent)
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
        Id curUserId = ContextHolder.getUserOrElseThrow().getId();
        uploadService.singleUpload(curUserId, cmd);
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadAppService#uploadWithChunks(UploadChunksEvent)
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
        Id curUserId = ContextHolder.getUserOrElseThrow().getId();
        uploadService.uploadPart(curUserId, uploadPartEvent);
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadAppService#getUploadProgress(UploadChunksProgressQuery)
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
        Id curUserId = ContextHolder.getUserOrElseThrow().getId();
        UploadProgressVO uploadProgress = uploadService.getUploadProgress(curUserId, progressQuery);
        return UploadChunksProgressDTO.Convertor.toUploadChunksProgressDTO(uploadProgress);
    }

    /**
     * {@inheritDoc}
     *
     * @see UploadAppService#mergeChunks(MergeChunksEvent)
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
        Id curUserId = ContextHolder.getUserOrElseThrow().getId();
        uploadService.mergePart(curUserId, mergePartEvent);
    }
}
