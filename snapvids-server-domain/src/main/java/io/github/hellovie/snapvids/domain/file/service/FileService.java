package io.github.hellovie.snapvids.domain.file.service;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.domain.file.entity.FileInfo;
import io.github.hellovie.snapvids.domain.file.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.file.event.UpdateFileStateCommand;

/**
 * 文件服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FileService {

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
}
