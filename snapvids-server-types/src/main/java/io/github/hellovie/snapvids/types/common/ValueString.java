package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.apache.commons.lang3.StringUtils;

import static io.github.hellovie.snapvids.common.module.common.CommonExceptionType.STRING_CANNOT_BE_EMPTY;

/**
 * [Domain Primitive] value string.
 * <p>有值字符串，不为 null 和空。</p>
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ValueString implements Verifiable {

    /**
     * 值
     */
    private final String value;

    /**
     * 错误信息
     */
    private final String errorMessage;

    /**
     * 构造 {@link ValueString} 对象，构造失败抛出异常。
     *
     * @param value 字符串值
     * @return {@link ValueString}
     */
    public static ValueString buildOrElseThrow(String value) {
        return new ValueString(value, "");
    }

    /**
     * 构造 {@link ValueString} 对象，构造失败抛出异常。
     *
     * @param value        字符串值
     * @param errorMessage 构造失败抛出异常的消息
     * @return {@link ValueString}
     */
    public static ValueString buildOrElseThrowByMessage(String value, String errorMessage) {
        return new ValueString(value, errorMessage);
    }

    /**
     * 私有构造。
     *
     * @param value        字符串值
     * @param errorMessage 字符串不符合要求的异常信息
     */
    private ValueString(String value, String errorMessage) {
        this.value = value;
        this.errorMessage = errorMessage;
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        if (StringUtils.isBlank(value)) {
            if (StringUtils.isBlank(errorMessage)) {
                throw new InvalidParamException(STRING_CANNOT_BE_EMPTY);
            }
            throw new InvalidParamException(STRING_CANNOT_BE_EMPTY, errorMessage);
        }
    }

    public String getValue() {
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
