package io.github.hellovie.snapvids.common.types;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;

/**
 * 若 Domain Primitive 是可自我校验数据的，则实现该接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
@FunctionalInterface
public interface Verifiable {

    // Todo：需要重新设计

    /**
     * 校验自身属性值，校验失败抛出异常。
     *
     * @throws InvalidParamException 校验失败抛出
     */
    void verify() throws InvalidParamException;
}
