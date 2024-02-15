package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 关注仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FollowRepository extends JpaRepository<Follow, Long> {
}
