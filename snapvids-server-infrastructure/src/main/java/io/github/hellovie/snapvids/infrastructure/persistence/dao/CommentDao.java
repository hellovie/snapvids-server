package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 评论仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("commentDao")
public interface CommentDao extends JpaRepository<Comment, Long> {
}
