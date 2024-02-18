package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.PERMISSION_CODE_CANNOT_BE_EMPTY;
import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.PERMISSION_CODE_FORMAT_NOT_MATCH;

/**
 * [Domain Primitive] permission code.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionCode implements Verifiable {

    /**
     * 值
     */
    private final String value;

    public PermissionCode(String value) {
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
        final String pattern = "^[a-zA-Z][a-zA-Z-:]+[a-zA-Z]$";
        Validation.isNotBlankOrElseThrow(value, PERMISSION_CODE_CANNOT_BE_EMPTY);
        Validation.isMatchOrElseThrow(value, pattern, PERMISSION_CODE_FORMAT_NOT_MATCH);
    }

    public String getValue() {
        return value;
    }
}
