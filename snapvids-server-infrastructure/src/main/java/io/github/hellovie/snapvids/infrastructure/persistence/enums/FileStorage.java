package io.github.hellovie.snapvids.infrastructure.persistence.enums;

/**
 * 存储方式。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum FileStorage {

    /**
     * 本地
     */
    LOCAL,

    /**
     * 文件服务器
     */
    FTP_SERVER,

    /**
     * 阿里云对象存储
     */
    OSS,

    /**
     * 腾讯云对象存储
     */
    COS
}
