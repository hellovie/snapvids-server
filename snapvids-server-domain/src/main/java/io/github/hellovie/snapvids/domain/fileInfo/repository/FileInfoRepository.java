package io.github.hellovie.snapvids.domain.fileInfo.repository;

import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileInfo;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 文件信息仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FileInfoRepository {

    /**
     * 保存 {@link FileInfo} 对象「新增 or 更新」。
     *
     * @param fileInfo 文件信息
     * @return 文件信息
     */
    FileInfo saveFileInfo(FileInfo fileInfo);

    /**
     * 更新文件状态。
     *
     * @param fileId    文件 id
     * @param fileState 文件状态
     * @param userId    用户 id
     */
    void updateFileState(Id fileId, FileState fileState, Id userId);

    /**
     * 根据 id 查询文件信息。
     *
     * @param id 文件 id
     * @return 文件信息，文件不存在返回 null
     */
    FileInfo findById(Id id);

    /**
     * 根据文件唯一标识和用户 id 查询文件信息。
     *
     * @param fileKey 文件唯一标识
     * @param userId  用户 id
     * @return 文件信息，文件不存在返回 null
     */
    FileInfo findByFileKeyAndUserId(FileKey fileKey, Id userId);
}
