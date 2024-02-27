package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.auth.service.UserAuthService;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 退出登录控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/logout")
public class LogoutController {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutController.class);

    @Resource(name = "userAuthService")
    private UserAuthService userAuthService;

    /**
     * 用户登出接口。
     *
     * @return {@link ResultResponse.SuccessResult} data: null
     */
    @GetMapping
    public ResultResponse.SuccessResult<Void> logout() {
        userAuthService.logout();
        return ResultResponse.success(null);
    }
}
