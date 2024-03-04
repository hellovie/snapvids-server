package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.upload.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.upload.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.upload.event.*;
import io.github.hellovie.snapvids.application.upload.service.FileUploadService;
import io.github.hellovie.snapvids.common.constants.UploadPath;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.common.util.TypeConvertor;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.interfaces.web.request.*;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.UNSUPPORTED_FILE_TYPES;

/**
 * 文件上传控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/files")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    @Resource(name = "fileUploadService")
    private FileUploadService fileUploadService;

    /**
     * 初始化上传，获取上传凭证。
     *
     * @param request 初始化上传表单
     * @return {@link ResultResponse.SuccessResult} data: {@link UploadTokenDTO}
     */
    @PostMapping("/upload/init")
    public ResultResponse.SuccessResult<UploadTokenDTO> initUpload(@RequestBody InitUploadRequest request) {
        LOG.info("[UploadController#initUpload入参]>>> originalName={}, fileKey={}, ext={} size={}",
                request.getOriginalName(), request.getFileKey(), request.getExt(), request.getSize());

        Validation.isEnumNameOrElseThrow(request.getExt(), FileExt.class, UNSUPPORTED_FILE_TYPES);
        InitUploadCommand cmd = new InitUploadCommand(
                new Filename(request.getOriginalName()),
                new FileKey(request.getFileKey()),
                (FileExt) TypeConvertor.toEnum(request.getExt(), FileExt.class),
                new FileSize(request.getSize()),
                new FilePath(UploadPath.DEFAULT.getRoot())
        );
        UploadTokenDTO uploadTokenDTO = fileUploadService.initUpload(cmd);
        return ResultResponse.success(uploadTokenDTO);
    }

    /**
     * 完成上传，更新文件状态。
     *
     * @param request 完成文件上传表单
     * @return {@link ResultResponse.SuccessResult} data: {@link FileInfoDTO}
     */
    @PostMapping("/upload/finish")
    public ResultResponse.SuccessResult<FileInfoDTO> finishUpload(@RequestBody FinishUploadRequest request) {
        LOG.info("[UploadController#finishUpload入参]>>> fileKey={}", request.getFileKey());
        FinishUploadCommand cmd = new FinishUploadCommand(new FileKey(request.getFileKey()));
        FileInfoDTO fileInfoDTO = fileUploadService.finishUpload(cmd);
        return ResultResponse.success(fileInfoDTO);
    }

    /**
     * 文件上传。
     *
     * @param request 上传文件请求
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @PostMapping("/upload")
    public ResultResponse.SuccessResult<Void> upload(@ModelAttribute UploadRequest request) {
        LOG.info("[UploadController#upload入参]>>> fileId={}, fileKey={}, md5={}, token={}",
                request.getFileId(), request.getFileKey(), request.getMd5(), request.getToken());

        UploadEvent event = new UploadEvent(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrowByMessage(request.getToken(), "非法上传"),
                request.getStartTime(),
                request.getExpiredTime(),
                new FileKey(request.getMd5()),
                new EffectiveFile(request.getFile())
        );
        fileUploadService.upload(event);
        return ResultResponse.success(null);
    }

    /**
     * 分片上传。
     *
     * @param request 分片上传请求
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @PostMapping("/upload/chunks")
    public ResultResponse.SuccessResult<Void> uploadWithChunks(@ModelAttribute UploadChunksRequest request) {
        LOG.info("[UploadController#uploadWithChunks入参]>>> fileId={}, fileKey={}, md5={}, token={}",
                request.getFileId(), request.getFileKey(), request.getMd5(), request.getToken());

        UploadChunksEvent event = new UploadChunksEvent(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrowByMessage(request.getToken(), "非法上传"),
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
        fileUploadService.uploadWithChunks(event);
        return ResultResponse.success(null);
    }

    /**
     * 获取上传进度。
     *
     * @param request 获取上传进度请求
     * @return {@link ResultResponse.SuccessResult} data: {@link UploadChunksProgressDTO}
     */
    @PostMapping("/upload/progress")
    public ResultResponse.SuccessResult<UploadChunksProgressDTO> getUploadProgress(
            @RequestBody GetUploadProgressRequest request) {
        LOG.info("[UploadController#getUploadProgress入参]>>> fileId={}, fileKey={}, token={}",
                request.getFileId(), request.getFileKey(), request.getToken());

        UploadChunksProgressQuery query = new UploadChunksProgressQuery(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrowByMessage(request.getToken(), "非法上传"),
                request.getStartTime(),
                request.getExpiredTime()
        );
        UploadChunksProgressDTO uploadProgress = fileUploadService.getUploadProgress(query);
        return ResultResponse.success(uploadProgress);
    }

    /**
     * 合并文件。
     *
     * @param request 合并文件请求
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @PostMapping("/upload/merge")
    public ResultResponse.SuccessResult<Void> mergeChunks(@RequestBody MergeChunksRequest request) {
        LOG.info("[UploadController#mergeChunks入参]>>> fileId={}, fileKey={}, token={}",
                request.getFileId(), request.getFileKey(), request.getToken());

        MergeChunksEvent event = new MergeChunksEvent(
                new Id(request.getFileId()),
                new FileKey(request.getFileKey()),
                ValueString.buildOrElseThrowByMessage(request.getToken(), "非法上传"),
                request.getStartTime(),
                request.getExpiredTime()
        );
        fileUploadService.mergeChunks(event);
        return ResultResponse.success(null);
    }
}
