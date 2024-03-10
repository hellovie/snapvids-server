package io.github.hellovie.snapvids.common.module.user;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionModule;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionSource;
import io.github.hellovie.snapvids.common.module.ExceptionModuleType;

import static io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType.*;

/**
 * 用户模块异常状态码枚举类。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum UserExceptionType implements ExceptionCode {

    /**
     * 用户名不能为空
     */
    USERNAME_CANNOT_BE_EMPTY(BUSINESS, 1, "用户名不能为空", true),

    /**
     * 用户名长度不符合要求
     */
    USERNAME_LEN_NOT_MATCH(BUSINESS, 2, "用户名长度应该在8到20之间", true),

    /**
     * 用户名格式不符合要求
     */
    USERNAME_FORMAT_NOT_MATCH(BUSINESS, 3, "用户名必须以字母开头，只包含字母、数字和下划线", true),

    /**
     * 密码不能为空
     */
    PASSWORD_CANNOT_BE_EMPTY(BUSINESS, 4, "密码不能为空", true),

    /**
     * 密码长度不符合要求
     */
    PASSWORD_LEN_NOT_MATCH(BUSINESS, 5, "密码长度应该在8到20之间", true),

    /**
     * 密码格式不符合要求
     */
    PASSWORD_FORMAT_NOT_MATCH(BUSINESS, 6, "密码必须由字母、数字和特殊字符中的任意2种组成", true),

    /**
     * 密码错误
     */
    WRONG_PASSWORD(BUSINESS, 7, "密码错误", true),

    /**
     * 密码错误太多次
     */
    WRONG_PASSWORD_TOO_MANY_TIMES(SECURITY, 8, "密码输入错误太多次，请稍后再试", false),

    /**
     * 手机号码不能为空
     */
    PHONE_CANNOT_BE_EMPTY(BUSINESS, 9, "手机号码不能为空", true),

    /**
     * 手机号码格式不符合要求
     */
    PHONE_FORMAT_NOT_MATCH(BUSINESS, 10, "手机号码格式有误", true),

    /**
     * 权限编码不能为空
     */
    PERMISSION_CODE_CANNOT_BE_EMPTY(BUSINESS, 11, "权限编码不能为空", true),

    /**
     * 权限编码以字母开头和结尾，同时只能包含字母、冒号和短横线
     */
    PERMISSION_CODE_FORMAT_NOT_MATCH(BUSINESS, 12, "权限编码以字母开头和结尾，同时只能包含字母、冒号和短横线", true),

    /**
     * 权限名称不能为空
     */
    PERMISSION_NAME_CANNOT_BE_EMPTY(BUSINESS, 13, "权限名称不能为空", true),

    /**
     * 错误的权限树层次
     */
    WRONG_PERMISSION_LEVEL(BUSINESS, 14, "错误的权限树层次", true),

    /**
     * 错误的权限排序编号
     */
    WRONG_PERMISSION_SORT_NUMBER(BUSINESS, 15, "错误的权限排序编号", true),

    /**
     * 角色标识不能为空
     */
    ROLE_KEY_CANNOT_BE_EMPTY(BUSINESS, 16, "角色标识不能为空", true),

    /**
     * 角色标识只能包含大写字母和下划线
     */
    ROLE_KEY_FORMAT_NOT_MATCH(BUSINESS, 17, "角色标识只能包含大写字母和下划线", true),

    /**
     * 角色名称不能为空
     */
    ROLE_NAME_CANNOT_BE_EMPTY(BUSINESS, 18, "角色名称不能为空", true),

    /**
     * 错误的IP地址
     */
    WRONG_IP_ADDRESS(BUSINESS, 19, "错误的IP地址", true),

    /**
     * 密码密文不能为空
     */
    PASSWORD_CIPHERTEXT_CANNOT_BE_EMPTY(SYSTEM, 20, "密码密文不能为空", false),

    /**
     * 注册参数不能为空
     */
    REGISTER_PARAMS_CANNOT_BE_NULL(BUSINESS, 21, "注册参数不能为空", true),

    /**
     * 登录参数不能为空
     */
    LOGIN_PARAMS_CANNOT_BE_NULL(BUSINESS, 22, "登录参数不能为空", true),

    /**
     * 访问令牌不能为空
     */
    ACCESS_TOKEN_CANNOT_BE_EMPTY(SYSTEM, 23, "访问令牌不能为空", false),

    /**
     * 刷新令牌不能为空
     */
    REFRESH_TOKEN_CANNOT_BE_EMPTY(SYSTEM, 24, "刷新令牌不能为空", false),

    /**
     * 无效的过期时间
     */
    INVALID_REFRESH_TOKEN_EXPIRATION_TIME(SYSTEM, 25, "无效的过期时间", false),

    /**
     * 找不到认证策略
     */
    AUTH_STRATEGY_NOT_FOUND(SYSTEM, 26, "找不到认证策略", false),

    /**
     * 用户已锁定，无法再登录
     */
    USER_LOCKED_CAN_NOT_LOGIN(BUSINESS, 27, "账号已锁定，无法登录", false),

    /**
     * 出现访问令牌数量大于刷新令牌的情况
     */
    ACCESS_TOKEN_NUM_EXCEEDS_REFRESH_TOKEN_NUM(SYSTEM, 28, "出现访问令牌数量大于刷新令牌的情况", false),

    /**
     * 该用户正在登录中，请稍后再试
     */
    USER_LOGGING_IN_TRY_AGAIN_LATER(BUSINESS, 29, "该用户正在登录中，请稍后再试", false),

    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXIST(BUSINESS, 30, "用户名已存在", true),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(BUSINESS, 31, "用户不存在", true),

    /**
     * 用户状态异常
     */
    USER_STATE_EXCEPTION(SYSTEM, 32, "用户状态异常", false),

    /**
     * 账号已被锁定
     */
    USER_STATE_LOCK(BUSINESS, 33, "账号已被锁定", false),

    /**
     * 账号已被禁用
     */
    USER_STATE_DISABLE(BUSINESS, 34, "账号已被禁用", false),

    /**
     * 更新用户登录信息失败
     */
    UPDATE_USER_LOGIN_INFO_FAILED(SYSTEM, 35, "更新用户登录信息失败", false),

    /**
     * 用户身份认证失败
     */
    USER_AUTH_EXCEPTION(BUSINESS, 36, "用户身份认证失败", false),

    /**
     * Account 不能为 null
     */
    ACCOUNT_CANNOT_BE_NULL(SYSTEM, 37, "Account不能为null", false),

    /**
     * TokenInfo 不能为 null
     */
    TOKEN_INFO_CANNOT_BE_NULL(SYSTEM, 38, "TokenInfo不能为null", false),

    /**
     * 用户无权限操作
     */
    USER_NOT_PERMISSION_TO_OPERATE(BUSINESS, 39, "你无权限操作", false),

    /**
     * 用户未登录
     */
    UNAUTHORIZED(BUSINESS, 9996, "用户未登录", false),

    /**
     * 无权限访问
     */
    FORBIDDEN(BUSINESS, 9997, "无权限访问", false),

    /**
     * 访问令牌已过期
     */
    ACCESS_TOKEN_HAS_EXPIRED(BUSINESS, 9998, "令牌已过期", true),

    /**
     * 刷新令牌已过期
     */
    REFRESH_TOKEN_HAS_EXPIRED(BUSINESS, 9999, "账号信息已过期, 请重新登录", false),

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

    UserExceptionType(ExceptionSource source, int number, String message, boolean canRetry) {
        this.module = ExceptionModuleType.USER_MODULE;
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
