package io.github.hellovie.snapvids.domain.util;

import io.github.hellovie.snapvids.common.context.Context;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;

/**
 * 用来保存本次请求所需的上下文信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ContextHolder {

    private static final ThreadLocal<Context> CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<SysUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    private ContextHolder() {}

    public static void setContext(Context context) {
        CONTEXT_THREAD_LOCAL.set(context);
    }

    public static Context getContext() {
        return CONTEXT_THREAD_LOCAL.get();
    }

    /**
     * 获取上下文，获取失败抛出异常。
     *
     * @return {@link Context}
     * @throws UtilException 获取失败抛出
     */
    public static Context getContextOrElseThrow() throws UtilException {
        Context context = CONTEXT_THREAD_LOCAL.get();
        if (context == null) {
            throw new UtilException(CommonExceptionType.GET_CONTEXT_FAIL);
        }
        return context;
    }

    public static void setUser(SysUser sysUser) {
        USER_THREAD_LOCAL.set(sysUser);
    }

    public static SysUser getUser() {
        return USER_THREAD_LOCAL.get();
    }

    /**
     * 获取当前用户，获取失败抛出异常。
     *
     * @return {@link SysUser}
     * @throws UtilException 获取失败抛出
     */
    public static SysUser getUserOrElseThrow() throws UtilException {
        SysUser sysUser = USER_THREAD_LOCAL.get();
        if (sysUser == null) {
            throw new UtilException(CommonExceptionType.GET_CURRENT_USER_FAIL);
        }
        return sysUser;
    }

    public static void clear() {
        CONTEXT_THREAD_LOCAL.remove();
        USER_THREAD_LOCAL.remove();
    }
}
