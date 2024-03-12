package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.file.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.file.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.file.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.file.event.InitUploadCommand;
import io.github.hellovie.snapvids.application.file.service.FileAppService;
import io.github.hellovie.snapvids.common.enums.FileExt;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.common.util.SpecialStringGenerator;
import io.github.hellovie.snapvids.common.util.TypeConvertor;
import io.github.hellovie.snapvids.infrastructure.service.upload.constants.UploadPath;
import io.github.hellovie.snapvids.interfaces.web.request.FinishUploadRequest;
import io.github.hellovie.snapvids.interfaces.web.request.InitUploadRequest;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.UNSUPPORTED_FILE_TYPES;

/**
 * 文件控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/files")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Resource(name = "fileAppService")
    private FileAppService fileAppService;

    /**
     * 初始化上传，获取上传凭证。
     *
     * @param request 初始化上传表单
     * @return {@link ResultResponse.SuccessResult} data: {@link UploadTokenDTO}
     */
    @PostMapping("/upload/init")
    public ResultResponse.SuccessResult<UploadTokenDTO> initUpload(@RequestBody InitUploadRequest request) {
        LOG.info("[FileController#initUpload入参]>>> originalName={}, fileKey={}, ext={} size={}",
                request.getOriginalName(), request.getFileKey(), request.getExt(), request.getSize());

        Validation.isEnumNameOrElseThrow(request.getExt(), FileExt.class, UNSUPPORTED_FILE_TYPES);
        InitUploadCommand cmd = new InitUploadCommand(
                new Filename(request.getOriginalName()),
                new FileKey(request.getFileKey()),
                (FileExt) TypeConvertor.toEnum(request.getExt(), FileExt.class),
                new FileSize(request.getSize()),
                new FilePath(UploadPath.DEFAULT.getPath() + "/" + SpecialStringGenerator.genTodayFormatted())
        );
        UploadTokenDTO uploadTokenDTO = fileAppService.initUpload(cmd);
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
        LOG.info("[FileController#finishUpload入参]>>> fileKey={}", request.getFileKey());
        FinishUploadCommand cmd = new FinishUploadCommand(new FileKey(request.getFileKey()));
        FileInfoDTO fileInfoDTO = fileAppService.finishUpload(cmd);
        return ResultResponse.success(fileInfoDTO);
    }

    /**
     * 获取文件临时访问路径。
     *
     * @param fileId 文件 id
     * @return {@link ResultResponse.SuccessResult} data: {@link String}
     */
    @GetMapping("/url/{fileId}/temp")
    public ResultResponse.SuccessResult<String> getTempUrl(@PathVariable("fileId") Long fileId) {
        LOG.info("[FileController#getTempUrl]>>> fileId={}", fileId);
        ValueString tempUrl = fileAppService.getTempUrlByFileId(new Id(fileId));
        return ResultResponse.success(tempUrl.getValue());
    }

    /**
     * 获取文件永久访问路径。
     *
     * @param fileId 文件 id
     * @return {@link ResultResponse.SuccessResult} data: {@link String}
     */
    @GetMapping("/url/{fileId}/forever")
    public ResultResponse.SuccessResult<String> getForeverUrl(@PathVariable("fileId") Long fileId) {
        LOG.info("[FileController#getForeverUrl]>>> fileId={}", fileId);
        ValueString foreverUrl = fileAppService.getForeverUrlByFileId(new Id(fileId));
        return ResultResponse.success(foreverUrl.getValue());
    }
}
