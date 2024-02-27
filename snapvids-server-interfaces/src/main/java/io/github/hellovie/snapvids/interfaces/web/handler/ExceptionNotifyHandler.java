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
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.ZonedDateTime;

/**
 * 异常通知处理器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("exceptionInfoHandler")
public class ExceptionNotifyHandler {

    @Resource(name = "exceptionManager")
    private ExceptionManager exceptionManager;

    @Resource(name = "notifyServiceManager")
    private NotifyServiceManager notifyServiceManager;

    /**
     * 发布警告级别的通知。
     *
     * @param ex 异常信息
     */
    public void notifyWarning(Exception ex) {
        ExceptionNotifyInfo notifyInfo = buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);
    }

    /**
     * 发布错误级别的通知。
     *
     * @param ex 异常信息
     */
    public void notifyError(Exception ex) {
        ExceptionNotifyInfo notifyInfo = buildExceptionNotifyInfo(ex);
        notifyServiceManager.notify(NotifyServiceType.DEFAULT.name(), notifyInfo);
        notifyServiceManager.notify(NotifyServiceType.CONSOLE.name(), notifyInfo);
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
        final Context context = ContextHolder.getContext();
        final String traceId = ContextHolder.getContext() == null ? "" : ContextHolder.getContext().getTraceId();

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
                traceId,
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
