package io.github.hellovie.snapvids.common.exception.manager;

/**
 * 异常来源接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface ExceptionSource {

    /**
     * 获取异常来源标识。
     *
     * @return 异常来源标识
     */
    String getLabel();

    /**
     * 获取异常来源名称。
     *
     * @return 异常来源名称
     */
    String getName();

    /**
     * 获取异常来源描述。
     *
     * @return 异常来源描述
     */
    String getDescription();
}
