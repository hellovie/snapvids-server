package io.github.hellovie.snapvids.common.module.upload;

/**
 * 上传模块缓存 Key。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadCacheKey {

    private UploadCacheKey() {}

    /**
     * 上传模块缓存 Key 前缀
     */
    private static final String PREFIX = "snapvids:upload:";

    /**
     * 上传模块锁缓存 Key 前缀
     */
    private static final String LOCK_PREFIX = PREFIX + "lock:";

    /**
     * 文件生成临时访问 url 锁
     */
    public static final String GEN_TEMP_URL_LOCK = LOCK_PREFIX + "gen-temp-url:";

    /**
     * 文件临时访问 url
     */
    public static final String TEMP_URL = PREFIX + "temp-url:";
}
