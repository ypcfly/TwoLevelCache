package com.ypc.spring.customcache.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

/**
 * @Author: ypcfly
 * @Date: 19-08-02 23:16
 * @Description: 自定义缓存
 */
@Slf4j
public class CaffeineRedisCache implements Cache {

    private String cacheName;

    // 一级缓存
    private Cache firstLevel;

    // 二级缓存
    private Cache secondLevel;

    public CaffeineRedisCache(String cacheName, Cache first, Cache second) {
        log.info(">>>> CaffeineRedisCache constructor start,params:cacheName={},firstCache={},secondCache={} <<<<",cacheName,first,second);
        this.cacheName = cacheName;
        this.firstLevel = first;
        this.secondLevel = second;
    }


    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        log.info(">>>> getNativeCache method <<<<");
        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
        log.info(">>>> get method key={} <<<<",key);
        ValueWrapper value = firstLevel.get(key);
        if (value == null) {
            // 二级缓存
            log.info(">>>> get cache object from second level <<<<");
            value = secondLevel.get(key);
            if (value != null) {
                Object result = value.get();
                firstLevel.put(key,result);
            }
        }

        return value;
    }

    @Override
    public <T> T get(Object key, Class<T> clazz) {
        log.info(">>>> get class method key={},clazz={} <<<<",key,clazz);
        T value = firstLevel.get(key, clazz);
        if (value == null) {
            log.info(">>>> get cache object from second level <<<<");
            value = secondLevel.get(key,clazz);

            if (value != null) {
                firstLevel.put(key,value);
            }
        }

        return value;
    }

    @Override
    public <T> T get(Object key, Callable<T> callable) {
        log.info(">>>> get callable method key={},callable={} <<<<",key,callable);
        T result = null;
        result = firstLevel.get(key,callable);

        if (result != null)
            return result;
        else {
            return secondLevel.get(key,callable);
        }
    }

    @Override
    public void put(Object key, Object value) {
        try {
            log.info(">>>> put method,key={},value={} <<<<",key,value);
            firstLevel.put(key,value);
            secondLevel.put(key,value);
        } catch (Exception e) {
            log.error(">>>> error message={} <<<<",e.getMessage());
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        log.info(">>>> putIfAbsent method,key={},value={} <<<<",key,value);
        firstLevel.putIfAbsent(key,value);

        return secondLevel.putIfAbsent(key,value);
    }

    @Override
    public void evict(Object o) {
        secondLevel.evict(o);
        firstLevel.evict(o);
    }

    @Override
    public void clear() {
        secondLevel.clear();
        firstLevel.clear();
    }
}
