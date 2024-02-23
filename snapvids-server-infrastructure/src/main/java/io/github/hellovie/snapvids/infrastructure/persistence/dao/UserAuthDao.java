package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户第三方认证信息仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("userAuthDao")
public interface UserAuthDao extends JpaRepository<UserAuth, Long> {
}
