package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Bgm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 作品 BGM 仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("bgmDao")
public interface BgmDao extends JpaRepository<Bgm, Long> {
}
