package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 关注仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("followDao")
public interface FollowDao extends JpaRepository<Follow, Long> {
}
