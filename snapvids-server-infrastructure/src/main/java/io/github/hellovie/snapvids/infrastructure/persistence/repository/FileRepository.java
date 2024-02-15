package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 文件仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FileRepository extends JpaRepository<File, Long> {
}
