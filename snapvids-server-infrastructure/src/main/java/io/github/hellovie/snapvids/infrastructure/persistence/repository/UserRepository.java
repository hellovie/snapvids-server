package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
