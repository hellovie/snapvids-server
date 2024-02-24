package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.CustomUserDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("userDao")
public interface UserDao extends JpaRepository<User, Long>, CustomUserDao {

    /**
     * 根据用户名获取用户。
     *
     * @param username 用户名
     * @return 用户
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据手机号码获取用户。
     *
     * @param phoneNumber 用户名
     * @return 用户
     */
    List<User> findByPhoneNumber(String phoneNumber);
}
