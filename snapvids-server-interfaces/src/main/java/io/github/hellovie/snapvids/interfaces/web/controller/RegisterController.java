package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.application.auth.event.UsernameRegisterCommand;
import io.github.hellovie.snapvids.application.auth.service.UserAuthService;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.interfaces.web.request.UsernameRegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                request.getUsername(),
                request.getPassword(),
                request.getPhoneNumber()
        );
        LoginUserDTO loginUserDTO = userAuthService.registerByUsername(cmd);
        return ResultResponse.success(loginUserDTO);
    }
}
