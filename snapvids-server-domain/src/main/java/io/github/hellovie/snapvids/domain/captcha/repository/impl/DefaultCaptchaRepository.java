package io.github.hellovie.snapvids.domain.captcha.repository.impl;

import io.github.hellovie.snapvids.common.cache.CacheService;
import io.github.hellovie.snapvids.domain.captcha.repository.CaptchaRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 验证码仓储实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("captchaRepository")
public class DefaultCaptchaRepository implements CaptchaRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCaptchaRepository.class);

    @Resource(name = "redisCacheService")
    private CacheService cacheService;

    /**
     * {@inheritDoc}
     *
     * @see CaptchaRepository#cacheCaptcha(String, String, long, TimeUnit)
     */
    @Override
    public void cacheCaptcha(String key, String value, long time, TimeUnit unit) {
        boolean isCacheSuccess = cacheService.setValue(key, value, time, unit);
        if (!isCacheSuccess) {
            LOG.warn("[Caching the captcha failed]>>> key={}, value={}, time={}, unit={}",
                    key, value, time, unit);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CaptchaRepository#getCaptchaFromCache(String)
     */
    @Override
    public String getCaptchaFromCache(String key) {
        String value = cacheService.getString(key);
        if (StringUtils.isBlank(value)) {
            return "";
        }
        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @see CaptchaRepository#removeCaptchaFromCache(String)
     */
    @Override
    public void removeCaptchaFromCache(String key) {
        cacheService.delete(key);
    }
}
