package io.github.hellovie.snapvids.common.module.common;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionModule;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionSource;
import io.github.hellovie.snapvids.common.module.ExceptionModuleType;

import static io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType.*;

/**
 * 通用模块异常状态码枚举类。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum CommonExceptionType implements ExceptionCode {

    /**
     * 这是一个业务异常测试案例
     */
    TEST_BUSINESS_EXCEPTION(BUSINESS, 1, "这是一个业务异常测试案例", true),

    /**
     * 找不到异常通知服务的实现类
     */
    NOTIFY_SERVICE_NOT_FOUND(ENVIRONMENT, 2, "找不到异常通知服务的实现类", false),

    /**
     * 异常通知服务注入失败
     */
    NOTIFY_SERVICE_INVALID_INJECTION(ENVIRONMENT, 3, "异常通知服务注入失败", false),

    /**
     * 参数不存在
     */
    PARAMS_NOT_FOUND(SYSTEM, 4, "参数不存在", false),

    /**
     * ID不存在
     */
    ID_NOT_FOUND(BUSINESS, 5, "ID不存在", false),

    /**
     * JWT Token 生成失败
     */
    CREATE_JWT_TOKEN_FAILED(SYSTEM, 6, "JWT Token生成失败", false),

    /**
     * 解密 JWT Token 失败
     */
    DECRYPT_JWT_TOKEN_FAILED(SYSTEM, 7, "JWT Token解密失败", false),

    /**
     * 验证码 id 不能为空
     */
    CAPTCHA_ID_CANNOT_BE_EMPTY(BUSINESS, 8, "验证码id不能为空", true),

    /**
     * 验证码不能为空
     */
    CAPTCHA_CANNOT_BE_EMPTY(BUSINESS, 9, "验证码不能为空", true),

    /**
     * 验证码长度不符合要求
     */
    CAPTCHA_LEN_NOT_MATCH(BUSINESS, 10, "验证码长度应该在4到6之间", true),

    /**
     * 验证码格式不符合要求
     */
    CAPTCHA_FORMAT_NOT_MATCH(BUSINESS, 11, "验证码只能包含字母和数字", true),

    /**
     * 验证码错误
     */
    WRONG_CAPTCHA(BUSINESS, 12, "验证码错误", true),

    /**
     * 字符串不能为空
     */
    STRING_CANNOT_BE_EMPTY(BUSINESS, 13, "字符串不能为空", true),

    /**
     * 获取请求上下文失败
     */
    GET_CONTEXT_FAILED(SYSTEM, 14, "获取请求上下文失败", false),

    /**
     * 获取请求用户失败
     */
    GET_CURRENT_USER_FAILED(SYSTEM, 15, "获取请求用户失败", false),

    /**
     * 类型转换失败
     */
    TYPE_CONVERSION_FAILED(SYSTEM, 16, "类型转换失败", false),

    /**
     * 未知异常
     */
    UNKNOWN_EXCEPTION(UNKNOWN, 9999, "服务器繁忙，请稍后再试", false),
    ;

    /**
     * 模块信息
     */
    private final ExceptionModule module;

    /**
     * 异常来源
     */
    private final ExceptionSource source;

    /**
     * 错误编号
     */
    private final int number;

    /**
     * 异常信息
     */
    private final String message;

    /**
     * 是否允许重试
     * <p>true：允许重试</p>
     */
    private final boolean canRetry;

    CommonExceptionType(ExceptionSource source, int number, String message, boolean canRetry) {
        this.module = ExceptionModuleType.COMMON_MODULE;
        this.source = source;
        this.number = number;
        this.message = message;
        this.canRetry = canRetry;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionCode#getModule()
     */
    @Override
    public ExceptionModule getModule() {
        return this.module;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionCode#getSource()
     */
    @Override
    public ExceptionSource getSource() {
        return this.source;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionCode#getNumber()
     */
    @Override
    public int getNumber() {
        return this.number;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionCode#getMessage()
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionCode#canRetry()
     */
    @Override
    public boolean canRetry() {
        return this.canRetry;
    }
}
