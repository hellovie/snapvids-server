package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.application.auth.event.UsernameLoginCommand;
import io.github.hellovie.snapvids.application.auth.service.UserAuthService;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.interfaces.web.request.UsernameLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        LOG.info("[LoginController#usernameLogin params]>>> username={}", request.getUsername());
        UsernameLoginCommand cmd = new UsernameLoginCommand(request.getUsername(), request.getPassword());
        LoginUserDTO loginUserDTO = userAuthService.loginByUsername(cmd);
        return ResultResponse.success(loginUserDTO);
    }
}
