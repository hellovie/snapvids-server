package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.PERMISSION_NAME_CANNOT_BE_EMPTY;

/**
 * [Domain Primitive] permission name.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionName implements Verifiable {

    /**
     * 值
     */
    private final String value;

    public PermissionName(String value) {
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
        Validation.isNotBlankOrElseThrow(value, PERMISSION_NAME_CANNOT_BE_EMPTY);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
