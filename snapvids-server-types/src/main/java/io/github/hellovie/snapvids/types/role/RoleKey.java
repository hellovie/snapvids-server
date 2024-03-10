package io.github.hellovie.snapvids.types.role;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.ROLE_KEY_CANNOT_BE_EMPTY;
import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.ROLE_KEY_FORMAT_NOT_MATCH;

/**
 * [Domain Primitive] role key.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class RoleKey extends DomainPrimitive {

    /**
     * 值
     */
    private final String value;

    public RoleKey(String value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);

        this.value = value;
    }

    public String getValue() {
        return "ROLE_" + value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        final String pattern = "^[A-Z_]+$";

        // 校验角色标识
        Validation.executeWithInvalidParamException(() -> {
            String _value = (String) params.get("value");
            Validation.isNotBlankOrElseThrow(_value, ROLE_KEY_CANNOT_BE_EMPTY);
            Validation.isMatchOrElseThrow(_value, pattern, ROLE_KEY_FORMAT_NOT_MATCH);
        }, ROLE_KEY_CANNOT_BE_EMPTY);

    }
}
