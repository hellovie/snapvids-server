package io.github.hellovie.snapvids.domain.storage.repository;

import io.github.hellovie.snapvids.domain.storage.entity.ChunkFileMetadata;
import io.github.hellovie.snapvids.domain.storage.entity.FileMetadata;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.ChunkNumber;
import io.github.hellovie.snapvids.types.file.FileKey;

import java.util.List;

/**
 * 文件存储仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface StorageRepository {

    /**
     * 根据文件唯一标识和用户 id 查询文件元数据。
     *
     * @param fileKey 文件唯一标识
     * @param userId  用户 id
     * @return 文件元数据，文件不存在返回 null
     */
    FileMetadata findByFileKeyAndUserId(FileKey fileKey, Id userId);

    /**
     * 根据文件 id 和分片编号查找分片信息。
     *
     * @param fileId     文件 id
     * @param currentNum 分片编号
     * @return 分片信息，分片不存在返回 null
     */
    ChunkFileMetadata findByFileIdAndCurrentNum(Id fileId, ChunkNumber currentNum);

    /**
     * 保存 {@link ChunkFileMetadata} 对象「新增 or 更新」。
     *
     * @param chunkFileMetadata 分片信息
     * @return 分片信息
     */
    ChunkFileMetadata saveChunkFileMetadata(ChunkFileMetadata chunkFileMetadata);

    /**
     * 根据文件 id 查询其所有分片。
     *
     * @param fileId 文件 id
     * @return 其分片，没有分片返回空集合
     */
    List<ChunkFileMetadata> findChunkFileMetadataByFileId(Id fileId);

    /**
     * 删除文件的所有分片。
     *
     * @param fileId 文件 id
     * @return 删除数量
     */
    long deleteAllChunkByFileId(Id fileId);
}
