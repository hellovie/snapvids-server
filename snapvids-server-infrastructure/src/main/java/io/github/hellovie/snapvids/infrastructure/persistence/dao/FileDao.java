package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("fileDao")
public interface FileDao extends JpaRepository<File, Long> {
}