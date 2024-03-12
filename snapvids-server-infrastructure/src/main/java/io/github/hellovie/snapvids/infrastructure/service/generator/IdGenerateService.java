package io.github.hellovie.snapvids.infrastructure.service.generator;

/**
 * ID 生成服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@FunctionalInterface
public interface IdGenerateService<T> {

    /**
     * 生成 ID。
     *
     * @return ID
     */
    T genId();
}
