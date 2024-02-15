package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 收藏仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CollectRepository extends JpaRepository<Collect, Long> {
}
