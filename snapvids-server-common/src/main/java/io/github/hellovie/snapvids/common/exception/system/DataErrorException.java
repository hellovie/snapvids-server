package io.github.hellovie.snapvids.common.exception.system;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;

/**
 * 「系统异常」数据错误异常，包含数据库数据被篡改、数据库数据与实际对不上...
 *
 * @author hellovie
 * @since 1.0.0
 */
public class DataErrorException extends SystemException {

    private static final long serialVersionUID = -1919588957823719452L;

    /**
     * 「不指定返回消息」非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public DataErrorException(ExceptionCode exceptionCode, Exception originalEx) {
        super(exceptionCode, originalEx);
    }

    /**
     * 「指定返回消息」非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param originalEx    原异常
     * @param returnCode    自定义返回给用户的异常信息
     */
    public DataErrorException(ExceptionCode exceptionCode, Exception originalEx, ExceptionCode returnCode) {
        super(exceptionCode, originalEx, returnCode);
    }

    /**
     * 「指定返回消息」主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param returnCode    自定义返回给用户的异常信息
     */
    public DataErrorException(ExceptionCode exceptionCode, ExceptionCode returnCode) {
        super(exceptionCode, returnCode);
    }

    /**
     * 「不指定返回消息」主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     */
    public DataErrorException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
