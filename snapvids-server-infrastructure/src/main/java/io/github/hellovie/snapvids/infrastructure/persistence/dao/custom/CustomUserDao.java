package io.github.hellovie.snapvids.infrastructure.persistence.dao.custom;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;

import java.sql.Timestamp;

/**
 * 自定义用户仓储接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CustomUserDao {

    /**
     * 仅更新用户登录信息，不更新其他属性。
     *
     * @param username      用户名
     * @param lastLoginIp   最后登录 ip
     * @param lastLoginTime 最后登录时间
     * @return 受影响的行数
     */
    long updateLoginInfoByUsername(String username, int lastLoginIp, Timestamp lastLoginTime);

    /**
     * 「单表」根据用户名查询用户，仅查询用户信息，不包含用户角色。
     *
     * @param username 用户名
     * @return 用户信息
     */
    User onlyFindUserByUsername(String username);
}
