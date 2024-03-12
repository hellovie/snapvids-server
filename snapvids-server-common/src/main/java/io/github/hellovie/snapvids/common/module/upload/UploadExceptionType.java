package io.github.hellovie.snapvids.common.module.upload;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionModule;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionSource;
import io.github.hellovie.snapvids.common.module.ExceptionModuleType;

import static io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType.BUSINESS;
import static io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType.SYSTEM;

/**
 * 上传模块异常状态码枚举类。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum UploadExceptionType implements ExceptionCode {

    /**
     * 上传令牌生成失败
     */
    UPLOAD_TOKEN_GENERATE_FAILED(SYSTEM, 1, "上传令牌生成失败", false),

    /**
     * 无效的上传令牌
     */
    INVALID_UPLOAD_TOKEN(BUSINESS, 2, "无效的上传令牌", true),

    /**
     * 超出单次上传的文件大小
     */
    UPLOAD_EXCEED_SIZE_LIMIT_FILE(BUSINESS, 3, "超出单次上传的文件大小", false),

    /**
     * 找不到文件资源
     */
    FILE_RESOURCE_NOT_FOUNT(BUSINESS, 4, "找不到文件资源", false),

    /**
     * 上传失败
     */
    UPLOAD_FILE_FAILED(BUSINESS, 5, "上传失败", true),

    /**
     * 找不到分片文件
     */
    CHUNK_FILE_NOT_FOUND(BUSINESS, 6, "找不到分片文件", false),

    /**
     * 缺少分片文件，无法合并
     */
    MISSING_CHUNK_FILE(BUSINESS, 7, "缺少分片文件，无法合并", false),

    /**
     * 数据库分片文件数据异常
     */
    DB_CHUNK_FILE_DATA_EXCEPTION(SYSTEM, 8, "数据库分片文件数据异常", false),

    /**
     * 合并文件失败
     */
    MERGE_CHUNK_FILE_FAILED(BUSINESS, 9, "合并文件失败", false),

    /**
     * 创建文件夹失败
     */
    CREATE_FOLDER_FAILED(SYSTEM, 10, "创建文件夹失败", false),

    /**
     * 保存文件到磁盘失败
     */
    FILE_SAVE_TO_DISK_FAILED(SYSTEM, 11, "无法将文件保存到磁盘", false),

    /**
     * 生成文件资源访问路径失败
     */
    GEN_FILE_RESOURCE_ACCESS_URL_FAILED(BUSINESS, 12, "生成文件资源访问路径失败", false),

    /**
     * 生成文件资源临时访问路径失败
     */
    GEN_FILE_RESOURCE_TEMP_ACCESS_URL_FAILED(BUSINESS, 13, "生成文件资源临时访问路径失败", false),

    /**
     * 生成文件资源系统路径失败
     */
    GEN_FILE_RESOURCE_SYSTEM_PATH_FAILED(BUSINESS, 14, "生成文件资源系统路径失败", false),

    /**
     * 文件资源不存在
     */
    FILE_RESOURCE_NOT_FOUND(BUSINESS, 15, "文件资源不存在", false),

    /**
     * 音频资源不存在
     */
    AUDIO_RESOURCE_NOT_FOUND(BUSINESS, 16, "音频资源不存在", false),

    /**
     * 图片资源不存在
     */
    IMAGE_RESOURCE_NOT_FOUND(BUSINESS, 17, "图片资源不存在", false),

    /**
     * 视频资源不存在
     */
    VIDEO_RESOURCE_NOT_FOUND(BUSINESS, 18, "视频资源不存在", false),

    /**
     * 资源已过期
     */
    FILE_TEMP_URL_HAS_EXPIRED(BUSINESS, 9998, "资源已过期", true),

    /**
     * 上传令牌已过期
     */
    UPLOAD_TOKEN_HAS_EXPIRED(BUSINESS, 9999, "上传令牌已过期", true),

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

    UploadExceptionType(ExceptionSource source, int number, String message, boolean canRetry) {
        this.module = ExceptionModuleType.UPLOAD_MODULE;
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
