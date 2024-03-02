package io.github.hellovie.snapvids.domain.storage.repository;

import io.github.hellovie.snapvids.domain.storage.entity.FileMetadata;
import io.github.hellovie.snapvids.types.file.FileIdentifier;

/**
 * 文件存储仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface StorageRepository {

    /**
     * 根据文件唯一标识查询文件元数据。
     *
     * @param fileIdentifier 文件唯一标识
     * @return 文件元数据，文件不存在返回 null
     */
    FileMetadata findByIdentifier(FileIdentifier fileIdentifier);
}
