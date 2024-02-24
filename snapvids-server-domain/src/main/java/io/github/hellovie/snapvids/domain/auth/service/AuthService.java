package io.github.hellovie.snapvids.domain.auth.service;

import io.github.hellovie.snapvids.common.exception.business.AuthException;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.auth.strategy.LoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.RegisterParams;
import io.github.hellovie.snapvids.domain.auth.vo.LoginInfo;

/**
 * 用户认证服务接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface AuthService {

    /**
     * 单个用户最大同时在线数.
     */
    int MAXIMUM_LOGIN_TOKEN_ID = 1;

    /**
     * 是否锁定用户（true：锁定）
     * <p>锁定用户后，当该用户的同时在线数超出限制时，无法再登录（申请新的 Token）。</p>
     */
    boolean WHETHER_LOCK_USER = false;

    /**
     * Access Token 过期时间 (单位: 秒).
     */
    long ACCESS_TOKEN_EXPIRED_IN_SECONDS = 15 * 60;

    /**
     * Refresh Token 过期时间 (单位: 秒).
     */
    long REFRESH_TOKEN_EXPIRED_IN_SECONDS = 7 * 24 * 60 * 60;

    /**
     * 用户注册。
     *
     * @param registerParams 用户注册所需信息
     * @return 用户登录信息
     */
    LoginInfo register(RegisterParams<?> registerParams);

    /**
     * 用户登录。
     *
     * @param loginParams 用户登录所需信息
     * @return 用户登录信息
     */
    LoginInfo login(LoginParams<?> loginParams);

    /**
     * 退出登录。
     */
    void logout();

    /**
     * 认证用户，认证成功返回当前请求的系统用户。
     *
     * @return 系统用户
     * @throws AuthException 认证失败后抛出
     */
    SysUser auth() throws AuthException;
}
