package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.ChunkFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 分片文件仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("chunkFileDao")
public interface ChunkFileDao extends JpaRepository<ChunkFile, Long> {
}
