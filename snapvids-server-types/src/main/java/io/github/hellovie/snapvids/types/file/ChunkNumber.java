package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.CHUNK_NUMBER_SIZE_NOT_MATCH;

/**
 * [Domain Primitive] chunk number.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ChunkNumber extends DomainPrimitive {

    /**
     * 分块编号
     */
    private final Integer value;

    public ChunkNumber(int value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 校验分片编号
        Validation.executeWithInvalidParamException(() -> {
            int _value = (int) params.get("value");
            Validation.inIntScopeOrElseThrow(_value, 1, Integer.MAX_VALUE, CHUNK_NUMBER_SIZE_NOT_MATCH);
        }, CHUNK_NUMBER_SIZE_NOT_MATCH);

    }
}