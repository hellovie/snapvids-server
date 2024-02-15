package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.ChunkFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 分片文件仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface ChunkFileRepository extends JpaRepository<ChunkFile, Long> {
}
