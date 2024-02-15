package io.github.hellovie.snapvids.common.exception.model;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionSource;

/**
 * 异常来源枚举。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum ExceptionSourceType implements ExceptionSource {

    /**
     * 系统内部
     */
    SYSTEM("A", "系统内部", "系统内部的异常可能包括代码错误、逻辑错误、资源不足、数据库连接问题等。"),

    /**
     * 系统安全
     */
    SECURITY("B", "系统安全", "安全漏洞、攻击、非法访问等可能导致异常情况。"),

    /**
     * 业务逻辑
     */
    BUSINESS("C", "业务逻辑", "用户输入非法数据，或者一些通过提示引导用户后，可以解决的异常。此类异常用户可以看到完整的异常信息。"),

    /**
     * 系统环境
     */
    ENVIRONMENT("D", "系统环境", "内部环境的异常可能是相关配置错误，外部环境的异常可能包括操作系统错误、硬件故障、网络中断等。"),

    /**
     * 第三方服务
     */
    THIRD_PARTY_SERVICE("E", "第三方服务", "当与第三方服务进行交互时，可能会发生各种异常情况，例如网络连接问题、超时、返回错误码等。"),

    /**
     * 未知来源
     */
    UNKNOWN("F", "未知来源", "未知的异常来源。"),

    ;

    /**
     * 异常来源标识
     */
    private final String label;

    /**
     * 异常来源名称
     */
    private final String name;

    /**
     * 异常来源描述
     */
    private final String description;

    ExceptionSourceType(String label, String name, String description) {
        this.label = label;
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionSource#getLabel()
     */
    @Override
    public String getLabel() {
        return this.label;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionSource#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionSource#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }
}
