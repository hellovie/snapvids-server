package io.github.hellovie.snapvids.common.exception.business;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;

/**
 * 「业务异常」非法参数异常。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class InvalidParamException extends BusinessException {

    private static final long serialVersionUID = -3009326972683742210L;

    /**
     * 非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public InvalidParamException(ExceptionCode exceptionCode, Exception originalEx) {
        super(exceptionCode, originalEx);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param message       自定义返回的消息
     */
    public InvalidParamException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     */
    public InvalidParamException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
