package io.github.hellovie.snapvids.common.types;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;

import java.util.Map;

/**
 * Domain Primitive.
 *
 * @author hellovie
 * @since 1.0.0
 */
public abstract class DomainPrimitive {

    /**
     * 「内部调用」校验自身属性值，校验失败抛出异常。
     *
     * @param params 参数
     * @throws InvalidParamException 校验失败抛出
     */
    protected abstract void verify(Map<String, Object> params) throws InvalidParamException;
}
