package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 喜欢仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("likeDao")
public interface LikeDao extends JpaRepository<Like, Long> {
}
