package io.github.hellovie.snapvids.types.role;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.ROLE_KEY_CANNOT_BE_EMPTY;
import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.ROLE_KEY_FORMAT_NOT_MATCH;

/**
 * [Domain Primitive] role key.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class RoleKey implements Verifiable {

    /**
     * 值
     */
    private final String value;

    public RoleKey(String value) {
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
        final String pattern = "^[A-Z_]+$";
        Validation.isNotBlankOrElseThrow(value, ROLE_KEY_CANNOT_BE_EMPTY);
        Validation.isMatchOrElseThrow(value, pattern, ROLE_KEY_FORMAT_NOT_MATCH);
    }

    public String getValue() {
        return "ROLE_" + value;
    }
}
