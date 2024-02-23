package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 分享仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("shareDao")
public interface ShareDao extends JpaRepository<Share, Long> {
}
