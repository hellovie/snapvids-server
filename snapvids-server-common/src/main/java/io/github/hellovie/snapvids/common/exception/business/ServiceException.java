package io.github.hellovie.snapvids.common.exception.business;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;

/**
 * 「业务异常」服务异常，业务逻辑有误抛出的异常，也可以理解为服务层抛出的异常。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ServiceException extends BusinessException {

    private static final long serialVersionUID = -4615073470023946383L;

    /**
     * 非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public ServiceException(ExceptionCode exceptionCode, Exception originalEx) {
        super(exceptionCode, originalEx);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param message       自定义返回的消息
     */
    public ServiceException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     */
    public ServiceException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
