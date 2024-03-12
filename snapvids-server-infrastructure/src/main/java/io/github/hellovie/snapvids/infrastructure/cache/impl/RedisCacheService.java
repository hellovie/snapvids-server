package io.github.hellovie.snapvids.infrastructure.cache.impl;

import io.github.hellovie.snapvids.infrastructure.cache.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("redisCacheService")
public class RedisCacheService implements CacheService {

    private static final Logger LOG = LoggerFactory.getLogger(RedisCacheService.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * {@inheritDoc}
     *
     * @see CacheService#hasKey(String)
     */
    @Override
    public boolean hasKey(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#setValue(String, Object)
     */
    @Override
    public boolean setValue(String key, Object value) {
        return setValue(key, value, CacheService.DEFAULT_CACHE_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#setValue(String, Object, long, TimeUnit)
     */
    @Override
    public boolean setValue(String key, Object value, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || value == null || unit == null) {
            return false;
        }

        if (timeout > 0L) {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getValue(Class, String)
     */
    @Override
    public <T> T getValue(Class<T> type, String key) {
        return getValueOrDefault(type, key, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getValueOrDefault(Class, String, Object)
     */
    @Override
    public <T> T getValueOrDefault(Class<T> type, String key, T defaultValue) {
        if (type == null || StringUtils.isBlank(key)) {
            return defaultValue;
        }

        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return type.cast(value);
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return defaultValue;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getString(String)
     */
    @Override
    public String getString(String key) {
        return getStringOrDefault(key, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getStringOrDefault(String, String)
     */
    @Override
    public String getStringOrDefault(String key, String defaultValue) {
        if (!hasKey(key)) {
            return defaultValue;
        }

        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return (String) value;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return defaultValue;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getLong(String)
     */
    @Override
    public Long getLong(String key) {
        return getLongOrDefault(key, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getLongOrDefault(String, Long)
     */
    @Override
    public Long getLongOrDefault(String key, Long defaultValue) {
        if (!hasKey(key)) {
            return defaultValue;
        }

        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        return defaultValue;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getInteger(String)
     */
    @Override
    public Integer getInteger(String key) {
        return getIntegerOrDefault(key, null);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getIntegerOrDefault(String, Integer)
     */
    @Override
    public Integer getIntegerOrDefault(String key, Integer defaultValue) {
        if (!hasKey(key)) {
            return defaultValue;
        }

        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return (Integer) value;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return defaultValue;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#delete(String)
     */
    @Override
    public void delete(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        redisTemplate.delete(key);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#deleteByPattern(String)
     */
    @Override
    public void deleteByPattern(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return;
        }

        Set<String> keys = redisTemplate.keys(pattern);
        if (keys == null || keys.size() == 0) {
            return;
        }
        redisTemplate.delete(keys);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#lock(String)
     */
    @Override
    public boolean lock(String key) {
        return lock(key, CacheService.DEFAULT_LOCK_DURATION, TimeUnit.MILLISECONDS);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#lock(String, long, TimeUnit)
     */
    @Override
    public boolean lock(String key, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || timeout <= 0 || unit == null) {
            return false;
        }

        String now = String.valueOf(System.currentTimeMillis());
        Boolean setSuccess = redisTemplate.opsForValue().setIfAbsent(key, now, timeout, unit);
        return setSuccess != null && setSuccess;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#isLock(String)
     */
    @Override
    public boolean isLock(String key) {
        return hasKey(key);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#unlock(String)
     */
    @Override
    public void unlock(String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        redisTemplate.delete(key);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#increment(String, long, TimeUnit)
     */
    @Override
    public Long increment(String key, long timeout, TimeUnit unit) {
        return increment(key, 1L, timeout, unit);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#increment(String, long, long, TimeUnit)
     */
    @Override
    public Long increment(String key, long delta, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || delta == 0 || timeout <= 0 || unit == null) {
            return null;
        }

        Long currNum = redisTemplate.opsForValue().increment(key, delta);
        redisTemplate.expire(key, timeout, unit);
        return currNum;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#addToMap(String, Map)
     */
    @Override
    public void addToMap(String key, Map<String, Object> map) {
        if (StringUtils.isBlank(key) || map == null || map.size() == 0) {
            return;
        }

        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getMap(String)
     */
    @Override
    public Map<String, Object> getMap(String key) {
        if (StringUtils.isBlank(key)) {
            return Collections.emptyMap();
        }

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries.size() == 0) {
            return Collections.emptyMap();
        }

        Map<String, Object> resultMap = new HashMap<>(entries.size());
        for (Object o : entries.keySet()) {
            resultMap.put(o.toString(), entries.get(o));
        }

        return resultMap;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#delInMap(String, Object...)
     */
    @Override
    public void delInMap(String key, Object... field) {
        if (StringUtils.isBlank(key) || field == null) {
            return;
        }

        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#addToSet(String, Object...)
     */
    @Override
    public void addToSet(String key, Object... value) {
        if (StringUtils.isBlank(key) || value == null) {
            return;
        }

        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getSet(String)
     */
    @Override
    public Set<Object> getSet(String key) {
        if (StringUtils.isBlank(key)) {
            return Collections.emptySet();
        }

        Set<Object> set = redisTemplate.opsForSet().members(key);
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#isInSet(String, Object)
     */
    @Override
    public boolean isInSet(String key, Object value) {
        if (StringUtils.isBlank(key) || value == null) {
            return false;
        }

        Boolean inSet = redisTemplate.opsForSet().isMember(key, value);
        return inSet != null && inSet;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#delInSet(String, Object...)
     */
    @Override
    public void delInSet(String key, Object... value) {
        if (StringUtils.isBlank(key) || value == null) {
            return;
        }

        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#setExpire(String, long, TimeUnit)
     */
    @Override
    public boolean setExpire(String key, long timeout, TimeUnit unit) {
        if (StringUtils.isBlank(key) || timeout == 0 || unit == null) {
            return false;
        }

        redisTemplate.expire(key, timeout, unit);
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @see CacheService#getExpire(String, TimeUnit)
     */
    @Override
    public long getExpire(String key, TimeUnit unit) {
        if (StringUtils.isBlank(key) || unit == null) {
            return 0;
        }

        Long expire = redisTemplate.getExpire(key, unit);
        if (expire == null) {
            return 0L;
        }

        return expire;
    }
}
