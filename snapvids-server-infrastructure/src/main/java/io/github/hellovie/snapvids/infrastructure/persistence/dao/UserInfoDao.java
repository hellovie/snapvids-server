package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户信息仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("userInfoDao")
public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
}
