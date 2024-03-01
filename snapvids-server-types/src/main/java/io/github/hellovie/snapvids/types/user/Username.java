package io.github.hellovie.snapvids.types.user;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.*;

/**
 * [Domain Primitive] username.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Username implements Verifiable {

    /**
     * 值
     */
    private final String value;

    public Username(String value) {
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
        final int max = 20;
        final int min = 8;
        final String pattern = "^[a-zA-Z]{1}\\w*$";
        Validation.isNotBlankOrElseThrow(value, USERNAME_CANNOT_BE_EMPTY);
        Validation.inIntScopeOrElseThrow(value.length(), min, max, USERNAME_LEN_NOT_MATCH);
        Validation.isMatchOrElseThrow(value, pattern, USERNAME_FORMAT_NOT_MATCH);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
