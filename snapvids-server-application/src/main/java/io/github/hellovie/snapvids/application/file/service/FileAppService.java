package io.github.hellovie.snapvids.application.file.service;

import io.github.hellovie.snapvids.application.file.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.file.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.file.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.file.event.InitUploadCommand;

/**
 * 文件应用服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FileAppService {

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
