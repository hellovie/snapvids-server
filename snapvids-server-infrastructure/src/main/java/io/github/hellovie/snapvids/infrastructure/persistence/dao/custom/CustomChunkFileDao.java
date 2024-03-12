package io.github.hellovie.snapvids.infrastructure.persistence.dao.custom;

/**
 * 自定义文件分片仓储接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CustomChunkFileDao {

    /**
     * 删除指定文件的所有分片记录。
     *
     * @param fileId 文件 id
     * @return 影响的行数
     */
    long deleteByFileId(long fileId);
}
