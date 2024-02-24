package io.github.hellovie.snapvids.common.module.user;

/**
 * 用户模块缓存 Key。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UserCacheKey {

    private UserCacheKey() {}

    /**
     * 用户模块缓存 Key 前缀
     */
    private static final String PREFIX = "snapvids:user:";

    /**
     * 用户「登录 / 注册」锁
     */
    public static final String USER_LOGIN_LOCK = PREFIX + "login-lock:";

    /**
     * 用户当前有效的访问令牌
     */
    public static final String USER_ACCESS_TOKEN = PREFIX + "access-token:";

    /**
     * 用户当前有效的刷新令牌
     */
    public static final String USER_REFRESH_TOKEN = PREFIX + "refresh-token:";

}
