package io.github.hellovie.snapvids.interfaces.web.handler;

import io.github.hellovie.snapvids.common.context.Context;
import io.github.hellovie.snapvids.common.exception.business.BusinessException;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionManager;
import io.github.hellovie.snapvids.common.exception.model.ExceptionType;
import io.github.hellovie.snapvids.common.exception.notify.ExceptionNotifyInfo;
import io.github.hellovie.snapvids.common.exception.notify.NotifyServiceManager;
import io.github.hellovie.snapvids.common.exception.notify.NotifyServiceType;
import io.github.hellovie.snapvids.common.exception.system.SystemException;
import io.github.hellovie.snapvids.common.exception.util.ExceptionUtils;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.common.util.ProjectUtils;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.interfaces.web.config.LoggerConfig;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.time.ZonedDateTime;

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

    @Resource(name = "notifyServiceManager")
    private NotifyServiceManager notifyServiceManager;

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
        ExceptionNotifyInfo notifyInfo = buildExceptionNotifyInfo(ex);
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
        // 异常告警
        ExceptionNotifyInfo notifyInfo = buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.DEFAULT.name(), notifyInfo);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);

        return ResultResponse.track(
                MDC.get(LoggerConfig.TRACE_ID),
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
        // 异常告警
        ExceptionNotifyInfo notifyInfo = buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.DEFAULT.name(), notifyInfo);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);

        return ResultResponse.track(
                MDC.get(LoggerConfig.TRACE_ID),
                exceptionManager.formatCode(CommonExceptionType.UNKNOWN_EXCEPTION),
                CommonExceptionType.UNKNOWN_EXCEPTION.getMessage()
        );
    }

    /**
     * 分三种情况构建异常通知信息。
     * <ol>
     *     <li>业务异常 {@link BusinessException}</li>
     *     <li>系统异常 {@link SystemException}</li>
     *     <li>未知异常 {@link Exception}</li>
     * </ol>
     *
     * @param ex 异常
     * @return 异常通知信息
     */
    private ExceptionNotifyInfo buildExceptionNotifyInfo(final Exception ex) {
        ExceptionType exceptionType;
        String code;
        // Todo：需要补充发生异常时的上下文
        final Context context = new Context();

        // 仅有系统异常和业务异常具有异常状态码
        if (ex instanceof BusinessException) {
            BusinessException bizEx = (BusinessException) ex;
            exceptionType = bizEx.getType();
            code = exceptionManager.formatCode(bizEx.getExceptionCode());
        } else if (ex instanceof SystemException) {
            SystemException sysEx = (SystemException) ex;
            exceptionType = sysEx.getType();
            code = exceptionManager.formatCode(sysEx.getExceptionCode());
        } else {
            exceptionType = ExceptionType.UNKNOWN;
            code = exceptionManager.formatCode(CommonExceptionType.UNKNOWN_EXCEPTION);
        }

        return new ExceptionNotifyInfo(
                ZonedDateTime.now(),
                ProjectUtils.getProjectName(),
                ProjectUtils.getProjectIp(),
                Thread.currentThread().getId(),
                MDC.get(LoggerConfig.TRACE_ID),
                ExceptionUtils.getFileName(ex),
                ExceptionUtils.getClassName(ex),
                ExceptionUtils.getMethodName(ex),
                ExceptionUtils.getLineNumber(ex),
                exceptionType,
                code,
                ex.getMessage(),
                context,
                ex.getCause() != null ? ex.getCause() : ex
        );
    }
}
