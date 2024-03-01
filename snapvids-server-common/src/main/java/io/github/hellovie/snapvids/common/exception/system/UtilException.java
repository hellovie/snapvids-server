package io.github.hellovie.snapvids.common.exception.system;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;

/**
 * 「系统异常」自定义工具类或第三方工具类发生的异常。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UtilException extends SystemException {

    private static final long serialVersionUID = -7482534018089484319L;

    /**
     * 非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public UtilException(ExceptionCode exceptionCode, Exception originalEx) {
        super(exceptionCode, originalEx);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param message       自定义返回的消息
     */
    public UtilException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     */
    public UtilException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
