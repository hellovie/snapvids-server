package io.github.hellovie.snapvids.interfaces.web.handler;

import com.alibaba.fastjson.JSON;
import io.github.hellovie.snapvids.common.exception.business.BusinessException;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionManager;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理匿名用户访问无权限资源时的异常。
 * <ul>
 *     <li>
 *         spring security 自带认证异常处理，在认证过程中所有抛出的异常将会被它处理，最后统一返回信息。
 *     </li>
 *     <li>
 *         如果使用 springboot 的全局异常处理的话，无法预知在认证过程中可能发生的所有异常。就无法做到对于认证失败后统一返回。
 *     </li>
 *     <li>
 *         例如：在认证期间发生非 {@link AuthenticationException} 的异常。
 *     </li>
 * </ul>
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("userAuthenticationEntryPoint")
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint {

    @Resource(name = "exceptionManager")
    private ExceptionManager exceptionManager;

    @Resource(name = "exceptionInfoHandler")
    private ExceptionNotifyHandler exceptionNotifyHandler;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Http Code: 401
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        // 如果抛出的是自定义业务异常，则按照自定义异常的异常信息返回给前端。
        ExceptionCode exceptionCode = UserExceptionType.UNAUTHORIZED;
        boolean canRetry = exceptionCode.canRetry();
        String code = exceptionManager.formatCode(exceptionCode);
        String message = exceptionCode.getMessage();

        Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (exception instanceof BusinessException) {
            BusinessException be = (BusinessException) exception;
            exceptionCode = be.getExceptionCode();
            canRetry = exceptionCode.canRetry();
            code = exceptionManager.formatCode(exceptionCode);
            message = exceptionCode.getMessage();

            // {@link io.github.hellovie.snapvids.domain.auth.service.AuthService#auth()}
            // {@link UserExceptionType.ACCESS_TOKEN_HAS_EXPIRED}
            // 如果 AuthService#auth() 中抛出的异常状态码是 ACCESS_TOKEN_HAS_EXPIRED。
            // 说明虽然 Token 认证失败了，但只是 Access Token 过期了，可以通过刷新令牌继续访问接口，无需重新登录。
            // 所以 Http Code = 200。
            String accessTokenExpired = exceptionManager.formatCode(UserExceptionType.ACCESS_TOKEN_HAS_EXPIRED);
            if (accessTokenExpired.equals(code)) {
                response.setStatus(HttpStatus.OK.value());
            }
        }

        // 异常告警
        exceptionNotifyHandler.notifyWarning(exception);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ResultResponse.FailResult fail = ResultResponse.fail(canRetry, code, message);
        response.getWriter().println(JSON.toJSONString(fail));
        response.getWriter().flush();
    }
}
