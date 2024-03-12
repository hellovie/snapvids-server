package io.github.hellovie.snapvids.common.module.file;

/**
 * 文件模块缓存 Key。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileCacheKey {

    private FileCacheKey() {}

    /**
     * 文件模块缓存 Key 前缀
     */
    private static final String PREFIX = "snapvids:file:";

    /**
     * 文件模块锁缓存 Key 前缀
     */
    private static final String LOCK_PREFIX = PREFIX + "lock:";
}
