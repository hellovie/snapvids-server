package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 分享仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface ShareRepository extends JpaRepository<Share, Long> {
}
