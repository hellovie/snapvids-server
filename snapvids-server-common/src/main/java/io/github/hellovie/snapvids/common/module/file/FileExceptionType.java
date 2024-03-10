package io.github.hellovie.snapvids.common.module.file;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionModule;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionSource;
import io.github.hellovie.snapvids.common.module.ExceptionModuleType;

import static io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType.*;

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
    FILE_KEY_CANNOT_BE_EMPTY(BUSINESS, 3, "文件标识不能为空", true),

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

    /**
     * 找不到存储服务
     */
    STORAGE_SERVICE_NOT_FOUND(SYSTEM, 9, "找不到存储服务", false),

    /**
     * 找不到默认存储服务
     */
    DEFAULT_STORAGE_SERVICE_NOT_FOUND(SYSTEM, 10, "找不到默认存储服务", false),

    /**
     * 找不到文件
     */
    FILE_NOT_FOUNT(BUSINESS, 11, "找不到文件", true),

    /**
     * 更新文件状态失败
     */
    UPDATE_FILE_STATE_FAILED(SYSTEM, 12, "更新文件状态失败", false),

    /**
     * 错误的文件状态
     */
    WRONG_FILE_STATE(BUSINESS, 13, "错误的文件状态", true),

    /**
     * 获取文件访问路径失败
     */
    GET_FILE_ACCESS_URL_FAILED(THIRD_PARTY_SERVICE, 14, "获取文件访问路径失败", false),

    /**
     * 文件还未上传成功
     */
    FILE_HAS_NOT_YET_BEEN_UPLOADED(BUSINESS, 15, "文件还未上传成功", true),

    /**
     * 文件已存在
     */
    FILE_ALREADY_EXIST(BUSINESS, 16, "文件已存在", false),

    /**
     * 上传令牌生成失败
     */
    UPLOAD_TOKEN_GENERATE_FAILED(SYSTEM, 17, "上传令牌生成失败", false),

    /**
     * 上传令牌已过期
     */
    UPLOAD_TOKEN_HAS_EXPIRED(BUSINESS, 18, "上传令牌已过期", true),

    /**
     * 无效的上传令牌
     */
    INVALID_UPLOAD_TOKEN(BUSINESS, 19, "无效的上传令牌", true),

    /**
     * 上传的文件不能为空
     */
    UPLOAD_FILE_CANNOT_BE_EMPTY(BUSINESS, 20, "上传的文件不能为空", true),

    /**
     * 上传失败
     */
    UPLOAD_FILE_FAILED(BUSINESS, 21, "上传失败", true),

    /**
     * 创建文件夹失败
     */
    CREATE_FOLDER_FAILED(SYSTEM, 22, "创建文件夹失败", false),

    /**
     * 保存文件到磁盘失败
     */
    FILE_SAVE_TO_DISK_FAILED(SYSTEM, 23, "无法将文件保存到磁盘", false),

    /**
     * 超出单次上传的文件大小
     */
    UPLOAD_EXCEED_SIZE_LIMIT_FILE(BUSINESS, 24, "超出单次上传的文件大小", false),

    /**
     * 找不到分片文件
     */
    CHUNK_FILE_NOT_FOUND(BUSINESS, 25, "找不到分片文件", false),

    /**
     * 缺少分片文件
     */
    MISSING_CHUNK_FILE(BUSINESS, 26, "缺少分片文件，请先上传", false),

    /**
     * 数据库分片文件数据异常
     */
    DB_CHUNK_FILE_DATA_EXCEPTION(SYSTEM, 27, "数据库分片文件数据异常", false),

    /**
     * 合并文件失败
     */
    MERGE_CHUNK_FILE_FAILED(BUSINESS, 28, "合并文件失败", false),

    /**
     * 不支持的文件类型
     */
    UNSUPPORTED_FILE_TYPES(BUSINESS, 29, "不支持的文件类型", true),

    /**
     * 上传令牌不能为空
     */
    UPLOAD_TOKEN_CANNOT_BE_EMPTY(BUSINESS, 30, "上传令牌不能为空", true),

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
