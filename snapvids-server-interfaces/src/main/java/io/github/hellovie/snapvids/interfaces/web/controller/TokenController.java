package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.application.auth.dto.TokenDTO;
import io.github.hellovie.snapvids.application.auth.service.UserAuthService;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 令牌控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    private static final Logger LOG = LoggerFactory.getLogger(TokenController.class);

    @Resource(name = "userAuthService")
    private UserAuthService userAuthService;

    /**
     * 刷新 Token 令牌.
     *
     * @return {@link ResultResponse.SuccessResult} data: {@link TokenDTO}
     */
    @GetMapping("/refresh")
    public ResultResponse.SuccessResult<TokenDTO> refresh() {
        TokenDTO tokenDTO = userAuthService.refreshToken();
        return ResultResponse.success(tokenDTO);
    }
}
