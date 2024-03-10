package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.common.CommonExceptionType.STRING_CANNOT_BE_EMPTY;

/**
 * [Domain Primitive] value string.
 * <p>有值字符串，不为 null 和空。</p>
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ValueString extends DomainPrimitive {

    /**
     * 值
     */
    private final String value;

    /**
     * 错误信息
     */
    private final ExceptionCode exceptionCode;

    /**
     * 构造 {@link ValueString} 对象，构造失败抛出异常。
     *
     * @param value 字符串值
     * @return {@link ValueString}
     */
    public static ValueString buildOrElseThrow(String value) {
        return new ValueString(value, null);
    }

    /**
     * 构造 {@link ValueString} 对象，构造失败抛出指定异常状态码的异常。
     *
     * @param value         字符串值
     * @param exceptionCode 异常状态码
     * @return {@link ValueString}
     */
    public static ValueString buildOrElseThrow(String value, ExceptionCode exceptionCode) {
        return new ValueString(value, exceptionCode);
    }

    /**
     * 私有构造。
     *
     * @param value         字符串值
     * @param exceptionCode 异常状态码
     */
    private ValueString(String value, ExceptionCode exceptionCode) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("value", value);
        params.put("exceptionCode", exceptionCode);
        verify(params);

        this.value = value;
        this.exceptionCode = exceptionCode;
    }

    public String getValue() {
        return value;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    @Override
    public String toString() {
        return value + "#" + exceptionCode;
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 校验字符串值
        Validation.executeWithInvalidParamException(() -> {
            String _value = (String) params.get("value");
            ExceptionCode _exceptionCode = (ExceptionCode) params.get("exceptionCode");
            if (StringUtils.isBlank(_value)) {
                if (_exceptionCode == null) {
                    throw new InvalidParamException(STRING_CANNOT_BE_EMPTY);
                }
                throw new InvalidParamException(_exceptionCode);
            }
        }, STRING_CANNOT_BE_EMPTY);
    }
}
