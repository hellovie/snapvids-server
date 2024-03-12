package io.github.hellovie.snapvids.application.auth.service.impl;

import io.github.hellovie.snapvids.application.auth.dto.GraphicalCaptchaDTO;
import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.application.auth.dto.TokenDTO;
import io.github.hellovie.snapvids.application.auth.event.SendSmsEvent;
import io.github.hellovie.snapvids.application.auth.event.UsernameLoginCommand;
import io.github.hellovie.snapvids.application.auth.event.UsernameRegisterCommand;
import io.github.hellovie.snapvids.application.auth.service.UserAuthAppService;
import io.github.hellovie.snapvids.domain.auth.service.AuthService;
import io.github.hellovie.snapvids.domain.auth.strategy.LoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.RegisterParams;
import io.github.hellovie.snapvids.domain.auth.strategy.impl.username.UsernameLoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.impl.username.UsernameRegisterParams;
import io.github.hellovie.snapvids.domain.auth.vo.LoginInfo;
import io.github.hellovie.snapvids.domain.auth.vo.TokenInfo;
import io.github.hellovie.snapvids.domain.captcha.event.CheckCaptchaEvent;
import io.github.hellovie.snapvids.domain.captcha.event.GenerateCaptchaCommand;
import io.github.hellovie.snapvids.domain.captcha.service.CaptchaService;
import io.github.hellovie.snapvids.infrastructure.service.draw.DrawService;
import io.github.hellovie.snapvids.types.common.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户认证应用服务默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("userAuthAppService")
public class DefaultUserAuthAppService implements UserAuthAppService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserAuthAppService.class);

    @Resource(name = "redisJwtAuthService")
    private AuthService authService;

    @Resource(name = "captchaService")
    private CaptchaService captchaService;

    @Resource(name = "captchaDrawService")
    private DrawService captchaDrawService;

    /**
     * {@inheritDoc}
     *
     * @see UserAuthAppService#registerByUsername(UsernameRegisterCommand)
     */
    @Override
    public LoginUserDTO registerByUsername(UsernameRegisterCommand command) {
        UsernameRegisterParams params = new UsernameRegisterParams(
                command.getUsername(),
                command.getPassword(),
                command.getPhoneNumber()
        );
        CheckCaptchaEvent checkCaptchaEvent = CheckCaptchaEvent.buildForRegisterSms(command.getCaptcha());

        captchaService.check(checkCaptchaEvent);
        LoginInfo loginInfo = authService.register(RegisterParams.Builder.buildByUsername(params));
        return LoginUserDTO.Convertor.toLoginUserDTO(loginInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthAppService#loginByUsername(UsernameLoginCommand)
     */
    @Override
    public LoginUserDTO loginByUsername(UsernameLoginCommand command) {
        UsernameLoginParams params = new UsernameLoginParams(
                command.getUsername(),
                command.getPassword()
        );
        CheckCaptchaEvent checkCaptchaEvent = CheckCaptchaEvent.buildForLoginGraphical(command.getCaptcha());

        captchaService.check(checkCaptchaEvent);
        LoginInfo loginInfo = authService.login(LoginParams.Builder.buildByUsername(params));
        return LoginUserDTO.Convertor.toLoginUserDTO(loginInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthAppService#logout()
     */
    @Override
    public void logout() {
        authService.logout();
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthAppService#refreshToken()
     */
    @Override
    public TokenDTO refreshToken() {
        TokenInfo tokenInfo = authService.refreshToken();
        return TokenDTO.Convertor.toTokenDTO(tokenInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthAppService#createCaptchaForUsernameLogin()
     */
    @Override
    public GraphicalCaptchaDTO createCaptchaForUsernameLogin() {
        GenerateCaptchaCommand cmd = GenerateCaptchaCommand.buildForLoginGraphical();
        Captcha captcha = captchaService.generate(cmd);
        StringBuffer base64 = new StringBuffer();
        captchaDrawService.drawToBase64(base64, captcha.getValue());
        return new GraphicalCaptchaDTO(captcha.getId(), base64.toString());
    }

    /**
     * {@inheritDoc}
     *
     * @see UserAuthAppService#sendSmsForUsernameRegister(SendSmsEvent)
     */
    @Override
    public void sendSmsForUsernameRegister(SendSmsEvent event) {
        GenerateCaptchaCommand cmd = GenerateCaptchaCommand.buildForRegisterSms(event.getPhoneNumber().getNumber());
        Captcha captcha = captchaService.generate(cmd);
        // Todo：需要去发送短信
        LOG.info("Todo: 向 {} 发送短信验证码 {}", captcha.getId(), captcha.getValue());
    }
}
