package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.common.CommonExceptionType.*;

/**
 * [Domain Primitive] captcha.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Captcha implements Verifiable {

    /**
     * 验证码 id
     */
    private final String id;

    /**
     * 验证码值
     */
    private final String value;

    public Captcha(String id, String value) {
        this.id = id;
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
        final int min = 4;
        final int max = 6;
        final String pattern = "^[a-zA-Z0-9]+$";
        Validation.isNotBlankOrElseThrow(id, CAPTCHA_ID_CANNOT_BE_EMPTY);
        Validation.isNotBlankOrElseThrow(value, CAPTCHA_CANNOT_BE_EMPTY);
        Validation.inIntScopeOrElseThrow(value.length(), min, max, CAPTCHA_LEN_NOT_MATCH);
        Validation.isMatchOrElseThrow(value, pattern, CAPTCHA_FORMAT_NOT_MATCH);
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
}
