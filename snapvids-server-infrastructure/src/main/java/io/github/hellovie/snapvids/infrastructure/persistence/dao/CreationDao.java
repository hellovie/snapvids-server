package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Creation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 作品仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("creationDao")
public interface CreationDao extends JpaRepository<Creation, Long> {
}
