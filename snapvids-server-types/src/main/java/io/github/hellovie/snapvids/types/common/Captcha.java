package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.common.CommonExceptionType.*;

/**
 * [Domain Primitive] captcha.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Captcha extends DomainPrimitive {

    /**
     * 验证码 id
     */
    private final String id;

    /**
     * 验证码值
     */
    private final String value;

    public Captcha(String id, String value) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", id);
        params.put("value", value);
        verify(params);

        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return id + "#" + value;
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        final int min = 4;
        final int max = 6;
        final String pattern = "^[a-zA-Z0-9]+$";

        // 校验验证码 ID
        Validation.executeWithInvalidParamException(() -> {
            String _id = (String) params.get("id");
            Validation.isNotBlankOrElseThrow(_id, CAPTCHA_ID_CANNOT_BE_EMPTY);
        }, CAPTCHA_ID_CANNOT_BE_EMPTY);
        // 校验验证码值
        Validation.executeWithInvalidParamException(() -> {
            String _value = (String) params.get("value");
            Validation.isNotBlankOrElseThrow(_value, CAPTCHA_CANNOT_BE_EMPTY);
            Validation.inIntScopeOrElseThrow(_value.length(), min, max, CAPTCHA_LEN_NOT_MATCH);
            Validation.isMatchOrElseThrow(_value, pattern, CAPTCHA_FORMAT_NOT_MATCH);
        }, CAPTCHA_CANNOT_BE_EMPTY);
    }
}
