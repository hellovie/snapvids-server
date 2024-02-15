package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 评论仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
