package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Creation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作品仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CreationRepository extends JpaRepository<Creation, Long> {
}
