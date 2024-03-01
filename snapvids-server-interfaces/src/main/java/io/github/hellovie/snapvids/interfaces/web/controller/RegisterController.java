package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.application.auth.event.SendSmsEvent;
import io.github.hellovie.snapvids.application.auth.event.UsernameRegisterCommand;
import io.github.hellovie.snapvids.application.auth.service.UserAuthService;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.interfaces.web.request.SendSmsRequest;
import io.github.hellovie.snapvids.interfaces.web.request.UsernameRegisterRequest;
import io.github.hellovie.snapvids.types.common.Captcha;
import io.github.hellovie.snapvids.types.common.PhoneNumber;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 注册控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);

    @Resource(name = "userAuthService")
    private UserAuthService userAuthService;

    /**
     * 用户名密码注册接口。
     *
     * @param request 用户名注册表单
     * @return {@link ResultResponse.SuccessResult} data: {@link LoginUserDTO}
     */
    @PostMapping("/username")
    public ResultResponse.SuccessResult<LoginUserDTO> usernameRegister(@RequestBody UsernameRegisterRequest request) {
        LOG.info("[RegisterController#usernameRegister params]>>> username={}", request.getUsername());
        UsernameRegisterCommand cmd = new UsernameRegisterCommand(
                new Username(request.getUsername()),
                Password.ofPlaintext(request.getPassword()),
                new PhoneNumber(request.getPhoneNumber()),
                new Captcha(request.getPhoneNumber(), request.getCaptcha())
        );
        LoginUserDTO loginUserDTO = userAuthService.registerByUsername(cmd);
        return ResultResponse.success(loginUserDTO);
    }

    /**
     * 发送短信验证码。
     *
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @GetMapping("/username/sms")
    public ResultResponse.SuccessResult<Void> sendSmsForUsernameRegister(@RequestBody SendSmsRequest request) {
        SendSmsEvent event = new SendSmsEvent(new PhoneNumber(request.getPhoneNumber()));
        userAuthService.sendSmsForUsernameRegister(event);
        return ResultResponse.success(null);
    }
}
