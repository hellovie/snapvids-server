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
     * 「不指定返回消息」非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public UtilException(ExceptionCode exceptionCode, Exception originalEx) {
        super(exceptionCode, originalEx);
    }

    /**
     * 「指定返回消息」非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param originalEx    原异常
     * @param returnCode    自定义返回给用户的异常信息
     */
    public UtilException(ExceptionCode exceptionCode, Exception originalEx, ExceptionCode returnCode) {
        super(exceptionCode, originalEx, returnCode);
    }

    /**
     * 「指定返回消息」主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param returnCode    自定义返回给用户的异常信息
     */
    public UtilException(ExceptionCode exceptionCode, ExceptionCode returnCode) {
        super(exceptionCode, returnCode);
    }

    /**
     * 「不指定返回消息」主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     */
    public UtilException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
