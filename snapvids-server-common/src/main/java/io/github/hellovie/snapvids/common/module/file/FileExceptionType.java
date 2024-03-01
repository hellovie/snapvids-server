package io.github.hellovie.snapvids.common.module.file;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionModule;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionSource;
import io.github.hellovie.snapvids.common.module.ExceptionModuleType;

import static io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType.BUSINESS;
import static io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType.SYSTEM;

/**
 * 文件模块异常状态码枚举类。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum FileExceptionType implements ExceptionCode {

    /**
     * 文件分片编号从1开始
     */
    CHUNK_NUMBER_SIZE_NOT_MATCH(BUSINESS, 1, "文件分片编号从1开始", true),

    /**
     * 文件分片总数异常
     */
    CHUNK_TOTAL_EXCEPTION(BUSINESS, 2, "文件分片总数异常", true),

    /**
     * 文件标识不能为空
     */
    FILE_IDENTIFIER_CANNOT_BE_EMPTY(BUSINESS, 3, "文件标识不能为空", true),

    /**
     * 无法解析 null 文件
     */
    UNABLE_TO_PARSE_NULL_FILE(BUSINESS, 4, "无法解析 null 文件", false),

    /**
     * 计算文件MD5异常
     */
    CALCULATE_FILE_MD5HEX_EXCEPTION(SYSTEM, 5, "计算文件MD5异常", false),

    /**
     * 文件名不能为空
     */
    FILENAME_CANNOT_BE_EMPTY(BUSINESS, 6, "文件名不能为空", true),

    /**
     * 文件路径不能为空
     */
    FILE_PATH_CANNOT_BE_EMPTY(BUSINESS, 7, "文件路径不能为空", true),

    /**
     * 文件大小异常
     */
    FILE_SIZE_EXCEPTION(BUSINESS, 8, "文件大小异常", true),
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

    FileExceptionType(ExceptionSource source, int number, String message, boolean canRetry) {
        this.module = ExceptionModuleType.FILE_MODULE;
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