package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.upload.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.upload.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.upload.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.upload.event.InitUploadCommand;
import io.github.hellovie.snapvids.application.upload.service.FileUploadService;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.common.util.TypeConvertor;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.interfaces.web.request.InitUploadRequest;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileIdentifier;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.WRONG_FILE_STATE;

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
        LOG.info("[UploadController#initUpload params]>>> originalName={}, identifier={}, ext={} size={}",
                request.getOriginalName(), request.getIdentifier(), request.getExt(), request.getSize());

        Validation.isEnumNameOrElseThrow(request.getExt(), FileExt.class, WRONG_FILE_STATE);
        InitUploadCommand cmd = new InitUploadCommand(
                new Filename(request.getOriginalName()),
                new FileIdentifier(request.getIdentifier()),
                (FileExt) TypeConvertor.toEnum(request.getExt(), FileExt.class),
                new FileSize(request.getSize())
        );
        UploadTokenDTO uploadTokenDTO = fileUploadService.initUpload(cmd);
        return ResultResponse.success(uploadTokenDTO);
    }

    /**
     * 完成上传，更新文件状态。
     *
     * @param id 文件 id
     * @return {@link ResultResponse.SuccessResult} data: {@link FileInfoDTO}
     */
    @PostMapping("/upload/{id}/finish")
    public ResultResponse.SuccessResult<FileInfoDTO> finishUpload(@PathVariable("id") Long id) {
        LOG.info("[UploadController#finishUpload params]>>> id={}", id);
        FinishUploadCommand cmd = new FinishUploadCommand(new Id(id));
        FileInfoDTO fileInfoDTO = fileUploadService.finishUpload(cmd);
        return ResultResponse.success(fileInfoDTO);
    }
}