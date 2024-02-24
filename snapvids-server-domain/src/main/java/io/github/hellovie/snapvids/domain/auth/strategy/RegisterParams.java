package io.github.hellovie.snapvids.domain.auth.strategy;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.domain.auth.strategy.impl.username.UsernameRegisterParams;

/**
 * 注册用户所需信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class RegisterParams<T> {

    /**
     * 认证类型
     */
    private final AuthStrategy.AuthType authType;

    /**
     * 注册参数
     */
    private final T params;

    private RegisterParams(AuthStrategy.AuthType authType, T params) {
        this.authType = authType;
        this.params = params;
    }

    public AuthStrategy.AuthType getAuthType() {
        return authType;
    }

    public T getParams() {
        return params;
    }

    /**
     * 建造者模式构造对象。
     */
    public static class Builder {

        /**
         * 构造使用「用户名 + 密码 + 手机号码」注册的参数对象。
         *
         * @param params 用户名注册参数对象
         * @return 统一注册参数对象
         * @throws InvalidParamException 参数为空抛出
         */
        public static RegisterParams<UsernameRegisterParams> buildByUsername(UsernameRegisterParams params)
                throws InvalidParamException {

            if (params == null) {
                throw new InvalidParamException(UserExceptionType.REGISTER_PARAMS_CANNOT_BE_NULL);
            }

            return new RegisterParams<>(AuthStrategy.AuthType.USERNAME_AUTH_STRATEGY, params);
        }
    }
}
