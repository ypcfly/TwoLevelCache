package com.ypc.spring.customcache.config;

import com.ypc.spring.customcache.cache.CacheType;
import com.ypc.spring.customcache.properties.CustomCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;

import java.util.HashMap;
import java.util.Map;

import static com.ypc.spring.customcache.cache.CacheType.*;

/**
 * @Author: ypcfly
 * @Date: 19-7-28 11:31
 * @Description: 自定义类，主要是管理cacheManager
 */
@Slf4j
public class CacheManagerContainer {

    private CacheManager redisCacheManager;

    private CacheManager caffeineCacheManager;

    private CacheManager noneCacheManager;

    private CustomCacheProperties customCacheProperties;

    public CustomCacheProperties getCustomCacheProperties() {
        return customCacheProperties;
    }

    private Map<String,CacheType> cacheTypeMap;

    public CacheManagerContainer() {
    }

    public CacheManagerContainer(CacheManager redisCacheManager, CacheManager caffeineCacheManager, CustomCacheProperties customCacheProperties) {
        this.redisCacheManager = redisCacheManager;
        this.caffeineCacheManager = caffeineCacheManager;
        this.noneCacheManager = new NoOpCacheManager();
        this.customCacheProperties = customCacheProperties;
        this.cacheTypeMap = new HashMap<>();

        for (Map.Entry entry : customCacheProperties.getContainers().entrySet()) {
            String key = (String) entry.getKey();
            CustomCacheProperties.Container container = (CustomCacheProperties.Container) entry.getValue();
            CacheType cacheType = container.getCacheType() == null ? customCacheProperties.getDefaultCacheType() : container.getCacheType();
            this.cacheTypeMap.put(key,cacheType);
        }
    }


    public CacheManagers getManagers(String name) {
        CacheManagers cacheManagers = null;
        CacheType cacheType = this.cacheTypeMap.get(name);
        if (CAFFEINE_REDIS.equals(cacheType)) {
            cacheManagers = new CacheManagerContainer.CacheManagers(caffeineCacheManager,redisCacheManager);
        } else if (CAFFEINE.equals(cacheType)) {
            cacheManagers = new CacheManagerContainer.CacheManagers(caffeineCacheManager,noneCacheManager);
        } else if (REDIS.equals(cacheType)){
            cacheManagers = new CacheManagerContainer.CacheManagers(redisCacheManager,noneCacheManager);
        } else {
            cacheManagers = new CacheManagerContainer.CacheManagers(noneCacheManager,noneCacheManager);
        }

        return cacheManagers;
    }

    public static class CacheManagers {
        private CacheManager levelOne;
        private CacheManager levelTwo;

        public CacheManagers(CacheManager firstLevel, CacheManager secondLevel) {
            this.levelOne = firstLevel;
            this.levelTwo = secondLevel;
        }

        public CacheManager getLevelOne() {
            return levelOne;
        }

        public CacheManager getLevelTwo() {
            return levelTwo;
        }
    }
}
