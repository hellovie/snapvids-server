package io.github.hellovie.snapvids.common.module;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionModule;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;

/**
 * 异常模块信息枚举。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum ExceptionModuleType implements ExceptionModule {

    COMMON_MODULE(0, "通用模块", "通用模块", CommonExceptionType.class),

    USER_MODULE(1, "用户模块", "用户模块", UserExceptionType.class),

    FILE_MODULE(2, "文件模块", "文件模块", FileExceptionType.class),

    ;

    /**
     * 异常模块编号
     */
    private final int number;

    /**
     * 异常模块名称
     */
    private final String name;

    /**
     * 异常模块描述
     */
    private final String description;

    /**
     * 异常模块绑定的异常状态码枚举的 Class
     */
    private final Class<? extends ExceptionCode> exceptionCodeClazz;

    ExceptionModuleType(int number, String name, String description, Class<? extends ExceptionCode> exceptionCodeClazz) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.exceptionCodeClazz = exceptionCodeClazz;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionModule#getNumber()
     */
    @Override
    public int getNumber() {
        return this.number;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionModule#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionModule#getDescription()
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * {@inheritDoc}
     *
     * @see ExceptionModule#getExceptionCodeClazz()
     */
    @Override
    public Class<? extends ExceptionCode> getExceptionCodeClazz() {
        return this.exceptionCodeClazz;
    }
}
