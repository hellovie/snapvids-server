package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户第三方认证信息仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
}
