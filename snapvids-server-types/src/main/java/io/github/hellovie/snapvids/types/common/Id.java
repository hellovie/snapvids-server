package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

/**
 * [Domain Primitive] id.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Id implements Verifiable {

    /**
     * id 值
     */
    private final Long value;

    public Id(long value) {
        this.value = value;
        verify();
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        Validation.inLongScopeOrElseThrow(value, 1, Long.MAX_VALUE, CommonExceptionType.ID_NOT_FOUND);
    }

    public Long getValue() {
        return value;
    }
}
