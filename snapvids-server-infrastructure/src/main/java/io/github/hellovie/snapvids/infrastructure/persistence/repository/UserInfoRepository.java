package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户信息仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
