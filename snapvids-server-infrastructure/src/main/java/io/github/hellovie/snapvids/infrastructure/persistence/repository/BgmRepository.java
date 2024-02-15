package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Bgm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 作品 BGM 仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface BgmRepository extends JpaRepository<Bgm, Long> {
}
