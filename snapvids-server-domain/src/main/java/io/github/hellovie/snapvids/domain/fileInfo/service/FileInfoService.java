package io.github.hellovie.snapvids.domain.fileInfo.service;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileInfo;
import io.github.hellovie.snapvids.domain.fileInfo.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.fileInfo.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.types.common.Id;

/**
 * 文件信息服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FileInfoService {

    /**
     * 创建文件信息。
     *
     * @param command 创建文件信息命令
     * @return 文件信息
     * @throws DataException 文件已存在抛出
     */
    FileInfo create(CreateFileInfoCommand command) throws DataException;

    /**
     * 更新文件状态。
     *
     * @param command 更新文件状态命令
     * @return 更新后的文件信息
     * @throws DataException 文件不存在抛出
     */
    FileInfo updateState(UpdateFileStateCommand command) throws DataException;

    /**
     * 根据文件 id 获取有效文件的信息。
     *
     * @param fileId 文件 id
     * @return 文件信息
     */
    FileInfo getValidFileInfoById(Id fileId);
}
