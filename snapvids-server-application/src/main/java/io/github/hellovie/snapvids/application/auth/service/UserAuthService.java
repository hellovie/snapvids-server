package io.github.hellovie.snapvids.application.auth.service;

import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.application.auth.dto.TokenDTO;
import io.github.hellovie.snapvids.application.auth.event.UsernameLoginCommand;
import io.github.hellovie.snapvids.application.auth.event.UsernameRegisterCommand;

/**
 * 用户认证服务接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface UserAuthService {

    /**
     * 用户名注册。
     *
     * @param command 注册参数
     * @return 登录用户 DTO
     */
    LoginUserDTO registerByUsername(UsernameRegisterCommand command);

    /**
     * 用户名登录。
     *
     * @param command 登录参数
     * @return 登录用户 DTO
     */
    LoginUserDTO loginByUsername(UsernameLoginCommand command);

    /**
     * 退出登录。
     */
    void logout();

    /**
     * 刷新令牌。
     *
     * @return 新的令牌
     */
    TokenDTO refreshToken();
}
