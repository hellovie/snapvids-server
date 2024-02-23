package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("userDao")
public interface UserDao extends JpaRepository<User, Long> {
}
