package io.github.hellovie.snapvids.common.exception.business;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.model.ExceptionType;

/**
 * 业务异常：用户能够通过提示自行解决的异常，不影响系统运行。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -7556405150391851824L;

    /**
     * 异常类型
     */
    private final ExceptionType type = ExceptionType.BUSINESS;

    /**
     * 异常信息
     */
    private final ExceptionCode exceptionCode;

    /**
     * 是否自定义消息
     */
    private final Boolean whetherCustomMessage;

    /**
     * 非主动抛出的异常，需要将原来的异常信息传递，不能吞掉异常。
     *
     * @param exceptionCode 开发者自定义的异常状态码
     * @param originalEx    原异常
     */
    public BusinessException(ExceptionCode exceptionCode, Exception originalEx) {
        super(originalEx.getMessage(), originalEx);
        this.exceptionCode = exceptionCode;
        this.whetherCustomMessage = Boolean.FALSE;
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     * @param message       自定义返回的消息
     */
    public BusinessException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.whetherCustomMessage = Boolean.TRUE;
        this.exceptionCode = exceptionCode;
    }

    /**
     * 主动抛出的异常。
     *
     * @param exceptionCode 开发者自定义的异常信息
     */
    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
        this.whetherCustomMessage = Boolean.FALSE;
    }

    /**
     * 获取异常类型，{@link BusinessException} 对应的异常类型是 {@link ExceptionType#BUSINESS}。
     *
     * @return {@link ExceptionType#BUSINESS}
     */
    public ExceptionType getType() {
        return this.type;
    }

    /**
     * 获取异常状态码，此异常状态码是开发者自定义的异常信息，属于主动抛出。
     *
     * @return 异常状态码
     */
    public ExceptionCode getExceptionCode() {
        return this.exceptionCode;
    }

    /**
     * 获取是否自定义消息的布尔值。
     *
     * @return true：自定义消息
     */
    public Boolean getWhetherCustomMessage() {
        return whetherCustomMessage;
    }
}
