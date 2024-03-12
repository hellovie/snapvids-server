package io.github.hellovie.snapvids.infrastructure.persistence.dao.custom;

import io.github.hellovie.snapvids.common.enums.FileState;

/**
 * 自定义文件仓储接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CustomFileDao {

    /**
     * 更新文件状态。
     *
     * @param id     文件 id
     * @param state  文件状态
     * @param userId 用户 id
     * @return 受影响的行数
     */
    long updateState(long id, FileState state, long userId);
}
