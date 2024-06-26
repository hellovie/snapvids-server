package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.PHONE_CANNOT_BE_EMPTY;
import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.PHONE_FORMAT_NOT_MATCH;

/**
 * [Domain Primitive] phone number.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PhoneNumber extends DomainPrimitive {

    /**
     * 手机号码
     */
    private final String number;

    public PhoneNumber(String number) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("number", number);
        verify(params);

        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    /**
     * 获取脱敏的手机号码。
     *
     * @return 脱敏的手机号码
     */
    public String getMaskedNumber() {
        if (number.trim().length() == 11) {
            return number.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }

        return String.join("", Collections.nCopies(number.trim().length(), "*"));
    }

    @Override
    public String toString() {
        return getMaskedNumber();
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        final String pattern = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

        // 校验手机号码
        Validation.executeWithInvalidParamException(() -> {
            String _number = (String) params.get("number");
            Validation.isNotBlankOrElseThrow(_number, PHONE_CANNOT_BE_EMPTY);
            Validation.isMatchOrElseThrow(_number, pattern, PHONE_FORMAT_NOT_MATCH);
        }, PHONE_CANNOT_BE_EMPTY);
    }
}
