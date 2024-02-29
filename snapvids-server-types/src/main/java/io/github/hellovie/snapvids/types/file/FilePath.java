package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.FILE_PATH_CANNOT_BE_EMPTY;

/**
 * [Domain Primitive] file path.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FilePath implements Verifiable {

    /**
     * 文件相对路径
     */
    private final String value;

    public FilePath(String value) {
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
        Validation.isNotBlankOrElseThrow(value, FILE_PATH_CANNOT_BE_EMPTY);
    }

    public String getValue() {
        return value;
    }
}
