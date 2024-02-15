package io.github.hellovie.snapvids.common.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CacheService {

    /**
     * 锁的默认持续时间（5 分钟）
     */
    long DEFAULT_LOCK_DURATION = 300_000L;

    /**
     * 默认缓存时间（30 分钟）
     */
    long DEFAULT_CACHE_TIME = 1800_000L;

    /**
     * 判断缓存中是否存在指定 Key。
     *
     * @param key Key
     * @return true：缓存中存在指定 Key
     */
    boolean hasKey(String key);

    /**
     * 缓存数据。
     * <p>默认缓存时间：{@link CacheService#DEFAULT_CACHE_TIME}</p>
     *
     * @param key   缓存 Key
     * @param value 缓存值
     * @return true：缓存成功
     */
    boolean setValue(String key, Object value);

    /**
     * 按指定时间缓存数据。
     *
     * @param key     缓存 Key
     * @param value   缓存值
     * @param timeout 缓存时间（-1：永不过期）
     * @param unit    时间单位
     * @return true：缓存成功
     */
    boolean setValue(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 获取指定类型的缓存值；若缓存不存在，则返回 null。
     *
     * @param type 缓存值类型
     * @param key  缓存 Key
     * @param <T>  返回的缓存值的类型
     * @return 缓存的值；若缓存不存在，则返回 null
     */
    <T> T getValue(Class<T> type, String key);

    /**
     * 获取指定类型的缓存值；若缓存不存在，则返回默认值。
     *
     * @param type         缓存值类型
     * @param key          缓存 Key
     * @param defaultValue 默认值
     * @param <T>          返回的缓存值的类型
     * @return 缓存的值；若缓存不存在，则返回默认值
     */
    <T> T getValueOrDefault(Class<T> type, String key, T defaultValue);

    /**
     * 获取 {@link String} 类型的缓存值；若缓存不存在，则返回 null。
     *
     * @param key 缓存 Key
     * @return 缓存的值；若缓存不存在，则返回 null
     */
    String getString(String key);

    /**
     * 获取 {@link String} 类型的缓存值；若缓存不存在，则返回默认值。
     *
     * @param key          缓存 Key
     * @param defaultValue 默认值
     * @return 缓存的值；若缓存不存在，则返回默认值
     */
    String getStringOrDefault(String key, String defaultValue);

    /**
     * 获取 {@link Long} 类型的缓存值；若缓存不存在，则返回 null。
     *
     * @param key 缓存 Key
     * @return 缓存的值；若缓存不存在，则返回 null
     */
    Long getLong(String key);

    /**
     * 获取 {@link Long} 类型的缓存值；若缓存不存在，则返回默认值。
     *
     * @param key          缓存 Key
     * @param defaultValue 默认值
     * @return 缓存的值；若缓存不存在，则返回默认值
     */
    Long getLongOrDefault(String key, Long defaultValue);

    /**
     * 获取 {@link Integer} 类型的缓存值；若缓存不存在，则返回 null。
     *
     * @param key 缓存 Key
     * @return 缓存的值；若缓存不存在，则返回 null
     */
    Integer getInteger(String key);

    /**
     * 获取 {@link Integer} 类型的缓存值；若缓存不存在，则返回默认值。
     *
     * @param key          缓存 Key
     * @param defaultValue 默认值
     * @return 缓存的值；若缓存不存在，则返回默认值
     */
    Integer getIntegerOrDefault(String key, Integer defaultValue);

    /**
     * 删除指定 Key 的缓存。
     *
     * @param key 缓存 Key
     */
    void delete(String key);

    /**
     * 删除符合模式串的缓存。
     *
     * @param pattern 模式串
     */
    void deleteByPattern(String pattern);

    /**
     * 锁，锁成功返回 true。
     * <p>默认锁持续时间 {@link CacheService#DEFAULT_LOCK_DURATION}</p>
     *
     * @param key 锁 Key
     * @return true：锁成功；false：锁失败（已被锁还没释放）
     */
    boolean lock(String key);

    /**
     * 指定锁持续时间的锁，锁成功返回 true。
     *
     * @param key     锁 Key
     * @param timeout 锁的持续时间（大于 0）
     * @param unit    时间单位
     * @return true：锁成功；false：锁失败（已被锁还没释放）
     */
    boolean lock(String key, long timeout, TimeUnit unit);

    /**
     * 查询指定锁是否存在，已被锁还没释放返回 true。
     *
     * @param key 锁 Key
     * @return true：已被锁还没释放
     */
    boolean isLock(String key);

    /**
     * 释放锁。
     *
     * @param key 锁 Key
     */
    void unlock(String key);

    /**
     * 对指定 Key 的缓存值进行自增 1 操作，并设置其过期时间。
     * <p>若缓存不存在，则先初始化缓存值为 0，再进行自增操作。</p>
     * <p>若缓存值的类型错误，则返回 null</p>
     *
     * @param key     缓存 Key
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 自增后的结果；若缓存值的类型错误，则返回 null
     */
    Long increment(String key, long timeout, TimeUnit unit);

    /**
     * 对指定 Key 的缓存值进行自增操作，并设置其过期时间。
     * <p>若缓存不存在，则先初始化缓存值为 0，再进行自增操作。</p>
     * <p>若缓存值的类型错误，则返回 null</p>
     *
     * @param key     缓存 Key
     * @param delta   自增步长
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 自增后的结果；若缓存值的类型错误，则返回 null
     */
    Long increment(String key, long delta, long timeout, TimeUnit unit);

    /**
     * 添加指定键值对到集合中；若集合不存在，则先创建集合再添加。
     *
     * @param key 集合 Key
     * @param map 键值对
     */
    void addToMap(String key, Map<String, Object> map);

    /**
     * 获取集合的所有键值对；集合不存在或集合为空，都返回空集合。
     *
     * @param key 集合 Key
     * @return 键值对 Map
     */
    Map<String, Object> getMap(String key);

    /**
     * 从集合中删除指定键值对。
     *
     * @param key   集合 Key
     * @param field 键值对 Key
     */
    void delInMap(String key, Object... field);

    /**
     * 添加指定元素到集合中；若集合不存在，则先创建集合再添加。
     *
     * @param key   集合 Key
     * @param value 元素值列表
     */
    void addToSet(String key, Object... value);

    /**
     * 获取集合所有元素；集合不存在或集合为空，都返回空集合。
     *
     * @param key 集合 Key
     * @return 元素集合；集合不存在或集合为空，都返回空集合
     */
    Set<Object> getSet(String key);

    /**
     * 判断元素是否存在集合内；集合不存在或元素不存在集合内，都返回 false。
     *
     * @param key   集合 Key
     * @param value 元素 Value
     * @return true：元素存在集合内；false：集合不存在或元素不存在集合内
     */
    boolean isInSet(String key, Object value);

    /**
     * 从集合中删除指定元素。
     *
     * @param key   集合 Key
     * @param value 元素 Value 列表
     */
    void delInSet(String key, Object... value);

    /**
     * 设置指定 Key 的缓存时间。
     *
     * @param key     缓存 Key
     * @param timeout 缓存时间，-1 则为永不过期
     * @param unit    时间单位
     * @return true：设置成功；false：设置失败（缓存不存在）
     */
    boolean setExpire(String key, long timeout, TimeUnit unit);

    /**
     * 获取指定 Key 的缓存剩余时长。
     * <ul>
     *     <li>0：缓存已失效或缓存不存在</li>
     *     <li>-1：缓存永久有效</li>
     * </ul>
     *
     * @param key  缓存 Key
     * @param unit 时间单位
     * @return 缓存剩余时常（0：缓存已失效或缓存不存在；-1：缓存永久有效）
     */
    long getExpire(String key, TimeUnit unit);
}
