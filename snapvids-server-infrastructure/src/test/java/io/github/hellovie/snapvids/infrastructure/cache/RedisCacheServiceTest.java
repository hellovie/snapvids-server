package io.github.hellovie.snapvids.infrastructure.cache;

import io.github.hellovie.snapvids.infrastructure.config.InfrastructureBeanConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis Cache Service Test.
 *
 * @author hellovie
 * @since 1.0.0
 */
@SpringBootTest(classes = InfrastructureBeanConfig.class)
public class RedisCacheServiceTest {

    @Resource(name = "redisCacheService")
    private CacheService cacheService;

    @Test
    void testAll() {
        testSetValue();
        testHasKey();
        testGetValue();
        testGetValueOrDefault();
        testGetString();
        testGetStringOrDefault();
        testGetLong();
        testGetLongOrDefault();
        testIncrement();
        testGetInteger();
        testGetIntegerOrDefault();
        testSetExpire();
        testGetExpire();
        testDelete();
        testLock();
        testIsLock();
        testUnlock();
        testAddToMap();
        testGetMap();
        testDelInMap();
        testAddToSet();
        testIsInSet();
        testGetSet();
        testDelInSet();
        testDeleteByPattern();
    }

    @Test
    void testHasKey() {
        boolean hasKey = cacheService.hasKey("snapvids");
        Assertions.assertTrue(hasKey, "hasKey(String) error");
    }

    @Test
    void testSetValue() {
        boolean setValue1Success = cacheService.setValue("snapvids", "短视频APP");
        boolean setValue2Success = cacheService.setValue("snapvids-one-minutes", "短视频APP", 1L,
                TimeUnit.MINUTES);
        boolean setValue3Success = cacheService.setValue("string-key", "string");
        boolean setValue4Success = cacheService.setValue("long-key", 999L);
        boolean setValue5Success = cacheService.setValue("integer-key", 999);
        Assertions.assertTrue(setValue1Success, "setValue(String, Object) error");
        Assertions.assertTrue(setValue2Success, "setValue(String, Object, long, TimeUnit) error");
        Assertions.assertTrue(setValue3Success, "setValue(String, Object) error");
        Assertions.assertTrue(setValue4Success, "setValue(String, Object) error");
        Assertions.assertTrue(setValue5Success, "setValue(String, Object) error");
    }

    @Test
    void testGetValue() {
        String snapvids = cacheService.getValue(String.class, "snapvids");
        Assertions.assertEquals("短视频APP", snapvids, "getValue(Class<T>, String) error");
    }

    @Test
    void testGetValueOrDefault() {
        String snapvids = cacheService.getValueOrDefault(String.class, "snapvids", "snapvids");
        String snapvidsNot = cacheService.getValueOrDefault(String.class, "snapvids-not", "snapvids");
        Assertions.assertEquals("短视频APP", snapvids, "getValueOrDefault(Class<T>, String, String) error");
        Assertions.assertEquals("snapvids", snapvidsNot, "getValueOrDefault(Class<T>, String, String) error");
    }

    @Test
    void testGetString() {
        String stringKey = cacheService.getString("string-key");
        Assertions.assertEquals("string", stringKey, "getString(String) error");
    }

    @Test
    void testGetStringOrDefault() {
        String snapvids = cacheService.getStringOrDefault("string-key", "string-default");
        String snapvidsNot = cacheService.getStringOrDefault("string-key-not", "string-default");
        Assertions.assertEquals("string", snapvids, "getStringOrDefault(String, String) error");
        Assertions.assertEquals("string-default", snapvidsNot, "getStringOrDefault(String, String) error");
    }

    @Test
    void testGetLong() {
        Long longKey = cacheService.getLong("long-key");
        Assertions.assertEquals(999L, longKey, "getLong(String) error");
    }

    @Test
    void testGetLongOrDefault() {
        Long longKey = cacheService.getLongOrDefault("long-key", 0L);
        Long longKeyNot = cacheService.getLongOrDefault("long-key-not", 0L);
        Assertions.assertEquals(999L, longKey, "getLongOrDefault(String, Long) error");
        Assertions.assertEquals(0L, longKeyNot, "getLongOrDefault(String, Long) error");
    }

    @Test
    void testGetInteger() {
        Integer integerKey = cacheService.getInteger("integer-key");
        Assertions.assertEquals(999, integerKey, "getInteger(String) error");
    }

    @Test
    void testGetIntegerOrDefault() {
        Integer integerKey = cacheService.getIntegerOrDefault("integer-key", 0);
        Integer integerKeyNot = cacheService.getIntegerOrDefault("integer-key-not", 0);
        Assertions.assertEquals(999, integerKey, "getIntegerOrDefault(String, Integer) error");
        Assertions.assertEquals(0, integerKeyNot, "getIntegerOrDefault(String, Integer) error");
    }

    @Test
    void testDelete() {
        Assertions.assertDoesNotThrow(() -> {
            cacheService.delete("snapvids");
        }, "delete(String) error");
    }

    @Test
    void testDeleteByPattern() {
        Assertions.assertDoesNotThrow(() -> {
            cacheService.deleteByPattern("*");
        }, "deleteByPattern(String) error");
    }

    @Test
    void testLock() {
        boolean notLock1 = cacheService.lock("lock");
        Assertions.assertTrue(notLock1, "lock(String) error");
        boolean lock1 = cacheService.lock("lock");
        Assertions.assertFalse(lock1, "lock(String) error");

        boolean notLock2 = cacheService.lock("lock-one-minutes", 1L, TimeUnit.MINUTES);
        Assertions.assertTrue(notLock2, "lock(String, long, TimeUnit) error");
        boolean lock2 = cacheService.lock("lock-one-minutes", 1L, TimeUnit.MINUTES);
        Assertions.assertFalse(lock2, "lock(String, long, TimeUnit) error");
    }


    @Test
    void testIsLock() {
        boolean lock = cacheService.isLock("lock");
        boolean notLock = cacheService.isLock("not-lock");
        Assertions.assertTrue(lock, "isLock(String) error");
        Assertions.assertFalse(notLock, "isLock(String) error");
    }

    @Test
    void testUnlock() {
        Assertions.assertDoesNotThrow(() -> {
            cacheService.unlock("lock");
        }, "unlock(String) error");
    }

    @Test
    void testIncrement() {
        Long increment = cacheService.increment("increment-key", 1L, TimeUnit.MINUTES);
        Long longKey = cacheService.increment("long-key", 2, 1L, TimeUnit.MINUTES);
        Assertions.assertEquals(1L, increment, "increment(String, long, TimeUnit) error");
        Assertions.assertEquals(1001L, longKey, "increment(String, long, long, TimeUnit) error");
    }

    @Test
    void testAddToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("one-field", "one-value");
        map.put("two-field", "two-value");
        map.put("three-field", "three-value");

        Assertions.assertDoesNotThrow(() -> {
            cacheService.addToMap("map-key", map);
        }, "addToMap(String, Map) error");
    }

    @Test
    void testGetMap() {
        Map<String, Object> map = cacheService.getMap("map-key");
        Assertions.assertEquals(3, map.size(), "getMap(String) error");
    }

    @Test
    void testDelInMap() {
        Assertions.assertDoesNotThrow(() -> {
            cacheService.delInMap("map-key", "one-field", "two-field");
        }, "delInMap(String, Object...) error");
    }

    @Test
    void testAddToSet() {
        Assertions.assertDoesNotThrow(() -> {
            cacheService.addToSet("set-key", "one-value");
        }, "addToSet(String, Object...)  error");
    }

    @Test
    void testGetSet() {
        Set<Object> set = cacheService.getSet("set-key");
        Assertions.assertEquals(1, set.size(), "getSet(String) error");
        Assertions.assertTrue(set.contains("one-value"), "getSet(String) error");
    }

    @Test
    void testIsInSet() {
        boolean inSet = cacheService.isInSet("set-key", "one-value");
        Assertions.assertTrue(inSet, "isInSet(String, String)");
    }

    @Test
    void testDelInSet() {
        Assertions.assertDoesNotThrow(() -> {
            cacheService.delInSet("set-key", "one-value");
        }, "delInSet(String, Object) error");
    }

    @Test
    void testSetExpire() {
        boolean setExpireSuccess = cacheService.setExpire("snapvids", 1L, TimeUnit.MINUTES);
        Assertions.assertTrue(setExpireSuccess, "setExpire(String, long, TimeUnit)");
    }

    @Test
    void testGetExpire() {
        long time = cacheService.getExpire("snapvids", TimeUnit.SECONDS);
        Assertions.assertTrue(time < 60, "getExpire(String, TimeUnit)");
    }
}