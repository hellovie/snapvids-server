package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 喜欢仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface LikeRepository extends JpaRepository<Like, Long> {
}
