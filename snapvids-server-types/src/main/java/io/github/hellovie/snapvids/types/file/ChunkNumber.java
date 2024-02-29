package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.CHUNK_NUMBER_SIZE_NOT_MATCH;

/**
 * [Domain Primitive] chunk number.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ChunkNumber implements Verifiable {

    /**
     * 分块编号
     */
    private final Integer value;

    public ChunkNumber(int value) {
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
        Validation.inIntScopeOrElseThrow(value, 1, Integer.MAX_VALUE, CHUNK_NUMBER_SIZE_NOT_MATCH);
    }

    public Integer getValue() {
        return value;
    }
}