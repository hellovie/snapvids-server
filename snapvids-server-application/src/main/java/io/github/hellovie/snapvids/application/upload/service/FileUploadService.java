package io.github.hellovie.snapvids.application.upload.service;

import io.github.hellovie.snapvids.application.upload.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.upload.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.upload.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.upload.event.InitUploadCommand;

/**
 * 文件上传服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FileUploadService {

    /**
     * 初始化上传。
     *
     * @param command 初始化上传命令
     * @return 上传文件许可
     */
    UploadTokenDTO initUpload(InitUploadCommand command);

    /**
     * 完成文件上传。
     *
     * @param command 完成文件上传命令
     * @return 文件信息 DTO
     */
    FileInfoDTO finishUpload(FinishUploadCommand command);
}
