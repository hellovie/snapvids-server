package io.github.hellovie.snapvids.common.exception.manager;

/**
 * 异常状态码接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface ExceptionCode {

    /**
     * 获取所属的模块信息。
     *
     * @return 模块信息
     */
    ExceptionModule getModule();

    /**
     * 获取异常来源。
     *
     * @return 异常来源
     */
    ExceptionSource getSource();

    /**
     * 获取错误编号（当前模块下的错误编号）。
     *
     * @return 错误编号
     */
    int getNumber();

    /**
     * 获取异常信息。
     *
     * @return 异常信息
     */
    String getMessage();

    // Todo：需要重新调整异常状态码是否能重试
    /**
     * 是否能重试。
     *
     * @return true：可以重试
     */
    boolean canRetry();
}
