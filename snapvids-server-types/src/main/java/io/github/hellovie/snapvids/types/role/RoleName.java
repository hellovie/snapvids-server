package io.github.hellovie.snapvids.types.role;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.ROLE_NAME_CANNOT_BE_EMPTY;

/**
 * [Domain Primitive] role name.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class RoleName implements Verifiable {

    /**
     * 值
     */
    private final String value;

    public RoleName(String value) {
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
        Validation.isNotBlankOrElseThrow(value, ROLE_NAME_CANNOT_BE_EMPTY);
    }

    public String getValue() {
        return value;
    }
}
