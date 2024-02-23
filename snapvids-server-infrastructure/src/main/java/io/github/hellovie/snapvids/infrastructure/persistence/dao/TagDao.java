package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 作品标签仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("tagDao")
public interface TagDao extends JpaRepository<Tag, Long> {
}
