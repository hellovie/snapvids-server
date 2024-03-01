package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.CHUNK_TOTAL_EXCEPTION;

/**
 * [Domain Primitive] chunk total.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ChunkTotal implements Verifiable {

    /**
     * 分块总数
     */
    private final Integer value;

    public ChunkTotal(int value) {
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
        Validation.inIntScopeOrElseThrow(value, 1, Integer.MAX_VALUE, CHUNK_TOTAL_EXCEPTION);
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}