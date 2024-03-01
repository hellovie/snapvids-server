package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.auth.dto.GraphicalCaptchaDTO;
import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.application.auth.event.UsernameLoginCommand;
import io.github.hellovie.snapvids.application.auth.service.UserAuthService;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.interfaces.web.request.UsernameLoginRequest;
import io.github.hellovie.snapvids.types.common.Captcha;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Resource(name = "userAuthService")
    private UserAuthService userAuthService;

    /**
     * 用户名密码登录接口。
     *
     * @param request 用户名登录表单
     * @return {@link ResultResponse.SuccessResult} data: {@link LoginUserDTO}
     */
    @PostMapping("/username")
    public ResultResponse.SuccessResult<LoginUserDTO> usernameLogin(@RequestBody UsernameLoginRequest request) {
        LOG.info("[LoginController#usernameLogin params]>>> username={}, captchaId={}, captcha={}",
                request.getUsername(), request.getCaptchaId(), request.getCaptcha());
        UsernameLoginCommand cmd = new UsernameLoginCommand(
                new Username(request.getUsername()),
                Password.ofPlaintext(request.getPassword()),
                new Captcha(request.getCaptchaId(), request.getCaptcha())
        );
        LoginUserDTO loginUserDTO = userAuthService.loginByUsername(cmd);
        return ResultResponse.success(loginUserDTO);
    }

    /**
     * 「Base64」生成用户名登录的图形验证码。
     *
     * @return {@link ResultResponse.SuccessResult} data: Base64 的图形验证码
     */
    @GetMapping("/username/captcha")
    public ResultResponse.SuccessResult<GraphicalCaptchaDTO> createCaptchaForUsernameLogin() {
        GraphicalCaptchaDTO captcha = userAuthService.createCaptchaForUsernameLogin();
        return ResultResponse.success(captcha);
    }
}
