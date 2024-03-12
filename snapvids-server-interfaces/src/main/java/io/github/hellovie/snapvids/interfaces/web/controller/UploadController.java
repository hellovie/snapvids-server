package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.upload.event.*;
import io.github.hellovie.snapvids.application.upload.service.UploadAppService;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.interfaces.web.request.GetUploadProgressRequest;
import io.github.hellovie.snapvids.interfaces.web.request.MergeChunksRequest;
import io.github.hellovie.snapvids.interfaces.web.request.UploadChunksRequest;
import io.github.hellovie.snapvids.interfaces.web.request.UploadRequest;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文件上传控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/uploads")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Resource(name = "localUploadAppService")
    private UploadAppService uploadAppService;

    /**
     * 文件上传。
     *
     * @param request 上传文件请求
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @PostMapping("/single")
    public ResultResponse.SuccessResult<Void> upload(@ModelAttribute UploadRequest request) {
        LOG.info("[UploadController#upload入参]>>> fileId={}, fileKey={}, md5={}, token={}",
                request.getFileId(), request.getFileKey(), request.getMd5(), request.getToken());

        UploadEvent event = new UploadEvent(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrow(request.getToken(), FileExceptionType.UPLOAD_TOKEN_CANNOT_BE_EMPTY),
                request.getStartTime(),
                request.getExpiredTime(),
                new FileKey(request.getMd5()),
                new EffectiveFile(request.getFile())
        );
        uploadAppService.upload(event);
        return ResultResponse.success(null);
    }

    /**
     * 分片上传。
     *
     * @param request 分片上传请求
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @PostMapping("/multipart/chunks")
    public ResultResponse.SuccessResult<Void> uploadWithChunks(@ModelAttribute UploadChunksRequest request) {
        LOG.info("[UploadController#uploadWithChunks入参]>>> fileId={}, fileKey={}, md5={}, token={}",
                request.getFileId(), request.getFileKey(), request.getMd5(), request.getToken());

        UploadChunksEvent event = new UploadChunksEvent(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrow(request.getToken(), FileExceptionType.UPLOAD_TOKEN_CANNOT_BE_EMPTY),
                request.getStartTime(),
                request.getExpiredTime(),
                new ChunkNumber(request.getCurrentNum()),
                new FileSize(request.getCurrentSize()),
                new FileSize(request.getChunkSize()),
                new FileSize(request.getTotalSize()),
                new ChunkTotal(request.getTotalChunks()),
                new FileKey(request.getMd5()),
                new EffectiveFile(request.getFile())
        );
        uploadAppService.uploadWithChunks(event);
        return ResultResponse.success(null);
    }

    /**
     * 获取上传进度。
     *
     * @param request 获取上传进度请求
     * @return {@link ResultResponse.SuccessResult} data: {@link UploadChunksProgressDTO}
     */
    @PostMapping("/multipart/progress")
    public ResultResponse.SuccessResult<UploadChunksProgressDTO> getUploadProgress(
            @RequestBody GetUploadProgressRequest request) {
        LOG.info("[UploadController#getUploadProgress入参]>>> fileId={}, fileKey={}, token={}",
                request.getFileId(), request.getFileKey(), request.getToken());

        UploadChunksProgressQuery query = new UploadChunksProgressQuery(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrow(request.getToken(), FileExceptionType.UPLOAD_TOKEN_CANNOT_BE_EMPTY),
                request.getStartTime(),
                request.getExpiredTime()
        );
        UploadChunksProgressDTO uploadProgress = uploadAppService.getUploadProgress(query);
        return ResultResponse.success(uploadProgress);
    }

    /**
     * 合并文件。
     *
     * @param request 合并文件请求
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @PostMapping("/multipart/merge")
    public ResultResponse.SuccessResult<Void> mergeChunks(@RequestBody MergeChunksRequest request) {
        LOG.info("[UploadController#mergeChunks入参]>>> fileId={}, fileKey={}, token={}",
                request.getFileId(), request.getFileKey(), request.getToken());

        MergeChunksEvent event = new MergeChunksEvent(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrow(request.getToken(), FileExceptionType.UPLOAD_TOKEN_CANNOT_BE_EMPTY),
                request.getStartTime(),
                request.getExpiredTime()
        );
        uploadAppService.mergeChunks(event);
        return ResultResponse.success(null);
    }
}
