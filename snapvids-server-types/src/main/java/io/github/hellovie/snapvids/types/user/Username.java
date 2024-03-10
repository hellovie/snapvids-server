package io.github.hellovie.snapvids.types.user;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.*;

/**
 * [Domain Primitive] username.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Username extends DomainPrimitive {

    /**
     * 值
     */
    private final String value;

    public Username(String value) {
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
        final int max = 20;
        final int min = 8;
        final String pattern = "^[a-zA-Z]{1}\\w*$";

        // 校验用户名
        Validation.executeWithInvalidParamException(() -> {
            String _value = (String) params.get("value");
            Validation.isNotBlankOrElseThrow(_value, USERNAME_CANNOT_BE_EMPTY);
            Validation.inIntScopeOrElseThrow(_value.length(), min, max, USERNAME_LEN_NOT_MATCH);
            Validation.isMatchOrElseThrow(_value, pattern, USERNAME_FORMAT_NOT_MATCH);
        }, USERNAME_CANNOT_BE_EMPTY);
    }
}
