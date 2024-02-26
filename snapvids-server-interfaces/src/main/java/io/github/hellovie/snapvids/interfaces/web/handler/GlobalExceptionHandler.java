package io.github.hellovie.snapvids.interfaces.web.handler;

import io.github.hellovie.snapvids.common.exception.business.BusinessException;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionManager;
import io.github.hellovie.snapvids.common.exception.notify.ExceptionNotifyInfo;
import io.github.hellovie.snapvids.common.exception.notify.NotifyServiceManager;
import io.github.hellovie.snapvids.common.exception.notify.NotifyServiceType;
import io.github.hellovie.snapvids.common.exception.system.SystemException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;

/**
 * 全局异常处理。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource(name = "exceptionManager")
    private ExceptionManager exceptionManager;

    @Resource(name = "exceptionInfoHandler")
    private ExceptionInfoHandler exceptionInfoHandler;

    @Resource(name = "notifyServiceManager")
    private NotifyServiceManager notifyServiceManager;

    /**
     * 无权限访问异常处理。
     * <p>Http Status: 403</p>
     * <p>捕获 Spring Security 的无权限访问异常类 {@link AccessDeniedException}。</p>
     * <p>全局异常处理的优先级要高于 {@link AccessDeniedHandler}，故在此捕获。</p>
     *
     * @param ex 业务异常
     * @return {@link ResultResponse.FailResult}
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultResponse accessDeniedHandler(AccessDeniedException ex) {
        final boolean canRetry = UserExceptionType.FORBIDDEN.canRetry();
        final String code = exceptionManager.formatCode(UserExceptionType.FORBIDDEN);
        final String message = UserExceptionType.FORBIDDEN.getMessage();

        // 异常告警
        ExceptionNotifyInfo notifyInfo = exceptionInfoHandler.buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);

        return ResultResponse.fail(canRetry, code, message);
    }

    /**
     * 业务异常处理器。
     * <p>Http Status: 200</p>
     *
     * @param ex 业务异常
     * @return {@link ResultResponse.FailResult}
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ResultResponse.FailResult businessExceptionHandler(final BusinessException ex) {
        final boolean canRetry = ex.getExceptionCode().canRetry();
        final String code = exceptionManager.formatCode(ex.getExceptionCode());
        final String message = ex.getExceptionCode().getMessage();

        // 异常告警
        ExceptionNotifyInfo notifyInfo = exceptionInfoHandler.buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);

        return ResultResponse.fail(canRetry, code, message);
    }

    /**
     * 系统异常处理器。
     * <p>Http Status: 500</p>
     *
     * @param ex 系统异常
     * @return {@link ResultResponse.TrackResult}
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SystemException.class)
    public ResultResponse.TrackResult systemExceptionHandler(final SystemException ex) {
        String traceId = ContextHolder.getContext() == null ? "" : ContextHolder.getContext().getTraceId();
        // 异常告警
        ExceptionNotifyInfo notifyInfo = exceptionInfoHandler.buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.DEFAULT.name(), notifyInfo);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);

        return ResultResponse.track(
                traceId,
                exceptionManager.formatCode(CommonExceptionType.UNKNOWN_EXCEPTION),
                CommonExceptionType.UNKNOWN_EXCEPTION.getMessage()
        );
    }

    /**
     * 未知异常处理器。
     * <p>Http Status: 500</p>
     *
     * @param ex 未知异常
     * @return {@link ResultResponse.TrackResult}
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse.TrackResult unknownExceptionHandler(final Exception ex) {
        String traceId = ContextHolder.getContext() == null ? "" : ContextHolder.getContext().getTraceId();
        // 异常告警
        ExceptionNotifyInfo notifyInfo = exceptionInfoHandler.buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.DEFAULT.name(), notifyInfo);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);

        return ResultResponse.track(
                traceId,
                exceptionManager.formatCode(CommonExceptionType.UNKNOWN_EXCEPTION),
                CommonExceptionType.UNKNOWN_EXCEPTION.getMessage()
        );
    }
}
