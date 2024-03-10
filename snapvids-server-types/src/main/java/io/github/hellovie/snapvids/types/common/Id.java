package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.common.CommonExceptionType.ID_NOT_FOUND;

/**
 * [Domain Primitive] id.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Id extends DomainPrimitive {

    /**
     * id 值
     */
    private final Long value;

    public Id(long value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 校验 ID 值
        Validation.executeWithInvalidParamException(() -> {
            long _value = (long) params.get("value");
            Validation.inLongScopeOrElseThrow(_value, 1, Long.MAX_VALUE, ID_NOT_FOUND);
        }, ID_NOT_FOUND);

    }
}
