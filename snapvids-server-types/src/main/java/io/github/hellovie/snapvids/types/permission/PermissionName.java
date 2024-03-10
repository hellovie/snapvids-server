package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.PERMISSION_NAME_CANNOT_BE_EMPTY;

/**
 * [Domain Primitive] permission name.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionName extends DomainPrimitive {

    /**
     * 值
     */
    private final String value;

    public PermissionName(String value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 校验权限名称
        Validation.executeWithInvalidParamException(() -> {
            String _value = (String) params.get("value");
            Validation.isNotBlankOrElseThrow(_value, PERMISSION_NAME_CANNOT_BE_EMPTY);
        }, PERMISSION_NAME_CANNOT_BE_EMPTY);
    }
}
