package io.github.hellovie.snapvids.application.file.service;

import io.github.hellovie.snapvids.application.file.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.file.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.file.event.FinishUploadCommand;
import io.github.hellovie.snapvids.application.file.event.InitUploadCommand;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;

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

    /**
     * 获取文件的永久访问地址。
     *
     * @param fileId 文件 id
     * @return 文件的永久访问地址
     * @throws DataException 获取失败抛出
     */
    ValueString getForeverUrlByFileId(Id fileId) throws DataException;

    /**
     * 获取文件的临时访问地址。
     *
     * @param fileId 文件 id
     * @return 文件的临时访问地址
     * @throws DataException 获取失败抛出
     */
    ValueString getTempUrlByFileId(Id fileId) throws DataException;
}
