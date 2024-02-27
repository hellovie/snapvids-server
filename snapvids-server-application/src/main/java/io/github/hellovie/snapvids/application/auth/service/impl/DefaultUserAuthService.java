package io.github.hellovie.snapvids.application.auth.service.impl;

import io.github.hellovie.snapvids.application.auth.convertor.LoginUserDTOConvertor;
import io.github.hellovie.snapvids.application.auth.convertor.TokenDTOConvertor;
import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.application.auth.dto.TokenDTO;
import io.github.hellovie.snapvids.application.auth.event.UsernameLoginCommand;
import io.github.hellovie.snapvids.application.auth.event.UsernameRegisterCommand;
import io.github.hellovie.snapvids.application.auth.service.UserAuthService;
import io.github.hellovie.snapvids.domain.auth.service.AuthService;
import io.github.hellovie.snapvids.domain.auth.strategy.LoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.RegisterParams;
import io.github.hellovie.snapvids.domain.auth.strategy.impl.username.UsernameLoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.impl.username.UsernameRegisterParams;
import io.github.hellovie.snapvids.domain.auth.vo.LoginInfo;
import io.github.hellovie.snapvids.domain.auth.vo.TokenInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户认证服务默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("userAuthService")
public class DefaultUserAuthService implements UserAuthService {

    @Resource(name = "loginUserDTOConvertor")
    private LoginUserDTOConvertor loginUserDTOConvertor;

    @Resource(name = "tokenDTOConvertor")
    private TokenDTOConvertor tokenDTOConvertor;

    @Resource(name = "redisJwtAuthService")
    private AuthService authService;

    /**
     * {@inheritDoc}
     *
     * @see UserAuthService#registerByUsername
     */
    @Override
    public LoginUserDTO registerByUsername(UsernameRegisterCommand command) {
        UsernameRegisterParams params = new UsernameRegisterParams(
                command.getUsername().getValue(),
                command.getPassword().getPlaintext(),
                command.getPhoneNumber().getNumber()
        );

        // Todo：需要短信验证码
        LoginInfo loginInfo = authService.register(RegisterParams.Builder.buildByUsername(params));
        return loginUserDTOConvertor.toLoginUserDTO(loginInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthService#loginByUsername
     */
    @Override
    public LoginUserDTO loginByUsername(UsernameLoginCommand command) {
        UsernameLoginParams params = new UsernameLoginParams(
                command.getUsername().getValue(),
                command.getPassword().getPlaintext()
        );

        // Todo：需要图片验证码
        LoginInfo loginInfo = authService.login(LoginParams.Builder.buildByUsername(params));
        return loginUserDTOConvertor.toLoginUserDTO(loginInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthService#logout
     */
    @Override
    public void logout() {
        authService.logout();
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthService#refreshToken
     */
    @Override
    public TokenDTO refreshToken() {
        TokenInfo tokenInfo = authService.refreshToken();
        return tokenDTOConvertor.toTokenDTO(tokenInfo);
    }
}
