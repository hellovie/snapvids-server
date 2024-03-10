package io.github.hellovie.snapvids.common.exception.business;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;

/**
 * 「业务异常」用户认证异常。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class AuthException extends BusinessException {

    private static final long serialVersionUID = -1780599297072365175L;

    /**
     * 非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public AuthException(ExceptionCode exceptionCode, Exception originalEx) {
        super(exceptionCode, originalEx);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     */
    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
