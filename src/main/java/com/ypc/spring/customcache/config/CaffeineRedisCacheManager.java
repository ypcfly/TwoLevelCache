package com.ypc.spring.customcache.config;

import com.ypc.spring.customcache.cache.CaffeineRedisCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: ypcfly
 * @Date: 19-8-3 19:34
 * @Description:
 */
public class CaffeineRedisCacheManager implements CacheManager {

    private CacheManagerContainer cacheManagerContainer;

    public CaffeineRedisCacheManager(CacheManagerContainer cacheManagerContainer) {
        this.cacheManagerContainer = cacheManagerContainer;
    }

//    protected Collection<? extends Cache> loadCaches() {
//        return Collections.emptySet();
//    }
//
//    protected Cache getMissingCache(String name) {
//        CacheManagerContainer.CacheManagers containers = cacheManagerContainer.getManagers(name);
//
//        return new CaffeineRedisCache(name,containers.getLevelOne().getCache(name),containers.getLevelTwo().getCache(name));
//    }

    @Override
    public Cache getCache(String name) {
        CacheManagerContainer.CacheManagers containers = cacheManagerContainer.getManagers(name);

        return new CaffeineRedisCache(name,containers.getLevelOne().getCache(name),containers.getLevelTwo().getCache(name));
    }

    @Override
    public Collection<String> getCacheNames() {
        String cacheName = cacheManagerContainer.getCustomCacheProperties().getCacheName();
        List<String> cacheNames = new ArrayList<>();
        cacheNames.add(cacheName);
        return cacheNames;
    }
}
