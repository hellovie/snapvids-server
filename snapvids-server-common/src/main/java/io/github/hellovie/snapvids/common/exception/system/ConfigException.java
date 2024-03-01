package io.github.hellovie.snapvids.common.exception.system;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;

/**
 * 「系统异常」配置异常。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ConfigException extends SystemException {

    private static final long serialVersionUID = 4335794145743790046L;

    /**
     * 非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public ConfigException(ExceptionCode exceptionCode, Exception originalEx) {
        super(exceptionCode, originalEx);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param message       自定义返回的消息
     */
    public ConfigException(ExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     */
    public ConfigException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
