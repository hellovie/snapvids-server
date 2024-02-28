package io.github.hellovie.snapvids.domain.captcha.repository;

import java.util.concurrent.TimeUnit;

/**
 * 验证码仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CaptchaRepository {

    /**
     * 缓存验证码。
     *
     * @param key   缓存 key
     * @param value 验证码的值
     * @param time  缓存时间
     * @param unit  时间单位
     */
    void cacheCaptcha(String key, String value, long time, TimeUnit unit);

    /**
     * 从缓存中获取验证码。
     *
     * @param key 缓存 key
     * @return 验证码的值，若不存在返回空字符串
     */
    String getCaptchaFromCache(String key);

    /**
     * 从缓存中移除验证码。
     *
     * @param key 缓存 key
     */
    void removeCaptchaFromCache(String key);
}
