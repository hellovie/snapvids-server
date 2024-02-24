package io.github.hellovie.snapvids.domain.auth.repository;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.Ip;
import io.github.hellovie.snapvids.types.user.Username;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 认证领域仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface AuthRepository {

    /**
     * 根据 {@link Username} 获取 {@link Account} 对象。
     *
     * @param username 用户名
     * @return {@link Account}，账号不存在返回 null
     */
    Account findAccountByUsername(Username username);

    /**
     * 根据 {@link Id} 获取当前用户在线的 Access Token 集合。
     *
     * @param id 用户 id
     * @return {@link Map} key: tokenId, value: expiredInSeconds；不存在返回空集合
     */
    Map<String, Long> findOnlineAccessTokenByUserId(Id id);

    /**
     * 根据 {@link Id} 获取当前用户在线的 Refresh Token 集合。
     *
     * @param id 用户 id
     * @return {@link Map} key: tokenId, value: expiredInSeconds；不存在返回空集合
     */
    Map<String, Long> findOnlineRefreshTokenByUserId(Id id);

    /**
     * 保存在线令牌 [Access Token, Refresh Token]
     *
     * @param id            用户 id
     * @param accessTokens  Access Token Map
     * @param refreshTokens Refresh Token Map
     */
    void saveOnlineToken(Id id, Map<String, Long> accessTokens, Map<String, Long> refreshTokens);

    /**
     * 保存 {@link SysUser} 对象「新增 or 更新」。
     *
     * @param sysUser 用户信息
     * @return 用户信息
     */
    SysUser saveSysUser(SysUser sysUser);

    /**
     * 仅更新用户登录信息，不更新其他属性。
     *
     * @param username      用户名
     * @param lastLoginIp   最后登录 ip
     * @param lastLoginTime 最后登录时间
     * @throws DataException 更新失败抛出异常
     */
    void updateLoginInfoByUsername(Username username, Ip lastLoginIp, Timestamp lastLoginTime) throws DataException;

    /**
     * 移除访问令牌和刷新令牌。
     *
     * @param userId  用户 id
     * @param tokenId 要移除的 token id
     */
    void removeToken(long userId, String... tokenId);
}
