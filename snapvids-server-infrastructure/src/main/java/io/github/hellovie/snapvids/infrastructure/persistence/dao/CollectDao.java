package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 收藏仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("collectDao")
public interface CollectDao extends JpaRepository<Collect, Long> {
}
