package com.ypc.spring.customcache.config;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ypcfly
 * @Date: 19-8-2 22:14
 * @Description:
 */
@Slf4j
public class CustomCaffeineCacheManager extends CaffeineCacheManager {

    private Map<String,Cache<Object,Object>> map = new HashMap();

    private boolean cacheNullValues;

    @Override
    protected org.springframework.cache.Cache createCaffeineCache(String name) {
        log.info(">>>> create CaffeineCache by name={} <<<<",name);
        return new CaffeineCache(name,createNativeCaffeineCache(name),cacheNullValues);
    }

    public CustomCaffeineCacheManager(Map<String, Cache<Object, Object>> map,boolean cacheNullValues) {
        log.info(">>>> CustomCaffeineCacheManager constructor start <<<<");
        this.map = map;
        this.cacheNullValues = cacheNullValues;
        super.setCacheNames(map.keySet());
    }

    protected Cache<Object,Object> createNativeCaffeineCache(String name) {
        log.info(">>>> createNativeCaffeineCache method start <<<<");
        return map.get(name);
    }
}
