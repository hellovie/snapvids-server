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
     * 密码的盐值不能为空
     */
    PASSWORD_SALT_CANNOT_BE_EMPTY(SYSTEM, 7, "密码的盐值不能为空", true),

    /**
     * 错误的密码盐值
     */
    WRONG_PASSWORD_SALT(SYSTEM, 8, "错误的密码盐值", true),

    /**
     * 密码错误
     */
    WRONG_PASSWORD(BUSINESS, 9, "密码错误", true),

    /**
     * 密码错误太多次
     */
    WRONG_PASSWORD_TOO_MANY_TIMES(SECURITY, 10, "密码输入错误太多次，请稍后再试", false),

    /**
     * 手机号码不能为空
     */
    PHONE_CANNOT_BE_EMPTY(BUSINESS, 11, "手机号码不能为空", true),

    /**
     * 手机号码格式不符合要求
     */
    PHONE_FORMAT_NOT_MATCH(BUSINESS, 12, "手机号码格式有误", true),

    /**
     * 权限编码不能为空
     */
    PERMISSION_CODE_CANNOT_BE_EMPTY(BUSINESS, 13, "权限编码不能为空", true),

    /**
     * 权限编码以字母开头和结尾，同时只能包含字母、冒号和短横线
     */
    PERMISSION_CODE_FORMAT_NOT_MATCH(BUSINESS, 14, "权限编码以字母开头和结尾，同时只能包含字母、冒号和短横线", true),

    /**
     * 权限名称不能为空
     */
    PERMISSION_NAME_CANNOT_BE_EMPTY(BUSINESS, 15, "权限名称不能为空", true),

    /**
     * 错误的权限树层次
     */
    WRONG_PERMISSION_LEVEL(BUSINESS, 16, "错误的权限树层次", true),

    /**
     * 错误的权限排序编号
     */
    WRONG_PERMISSION_SORT_NUMBER(BUSINESS, 17, "错误的权限排序编号", true),

    /**
     * 角色标识不能为空
     */
    ROLE_KEY_CANNOT_BE_EMPTY(BUSINESS, 18, "角色标识不能为空", true),

    /**
     * 角色标识只能包含大写字母和下划线
     */
    ROLE_KEY_FORMAT_NOT_MATCH(BUSINESS, 19, "角色标识只能包含大写字母和下划线", true),

    /**
     * 角色名称不能为空
     */
    ROLE_NAME_CANNOT_BE_EMPTY(BUSINESS, 20, "角色名称不能为空", true),

    /**
     * 错误的IP地址
     */
    WRONG_IP_ADDRESS(BUSINESS, 21, "错误的IP地址", true),

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
