package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作品标签仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
