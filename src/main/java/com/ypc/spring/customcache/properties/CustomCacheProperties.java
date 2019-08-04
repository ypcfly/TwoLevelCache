package com.ypc.spring.customcache.properties;

import com.ypc.spring.customcache.cache.CacheType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ypcfly
 * @Date: 19-7-27 23:39
 * @Description:
 */
@Configuration
@ConfigurationProperties(prefix = "com.ypc.custom.cache")
public class CustomCacheProperties {
    // 缓存名称
    private String cacheName;
    // 默认缓存类型
    private CacheType defaultCacheType;

    // 缓存多少秒过期,默认为0,不过期
    private Integer expireSecond;

    private Map<String,CustomCacheProperties.Container> containers;

    // 是否缓存null值
    private boolean cacheNullValues;

    public CustomCacheProperties() {
        this.defaultCacheType = CacheType.CAFFEINE_REDIS;
        this.expireSecond = 0;
        this.containers = new HashMap<>();
        this.cacheNullValues = false;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public CacheType getDefaultCacheType() {
        return defaultCacheType;
    }

    public void setDefaultCacheType(CacheType defaultCacheType) {
        this.defaultCacheType = defaultCacheType;
    }

    public Integer getExpireSecond() {
        return expireSecond;
    }

    public void setExpireSecond(Integer expireSecond) {
        this.expireSecond = expireSecond;
    }

    public Map<String,CustomCacheProperties.Container> getContainers() {
        return containers;
    }

    public void setContainers(Map<String,CustomCacheProperties.Container> containers) {
        this.containers = containers;
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public void setCacheNullValues(boolean cacheNullValues) {
        this.cacheNullValues = cacheNullValues;
    }

    /**
     * 自定义缓存的容器配置
     */
    public static class Container {

        // 缓存类型
        private CacheType cacheType;

        // 最大大小
        private Integer maximumSize;

        // 初始容量
        private Integer initialCapacity;

        // 访问多久后过期
        private Integer expireAfterAccess;

        public Container() {
        }

        public CacheType getCacheType() {
            return cacheType;
        }

        public void setCacheType(CacheType cacheType) {
            this.cacheType = cacheType;
        }

        public Integer getMaximumSize() {
            return maximumSize;
        }

        public void setMaximumSize(Integer maximumSize) {
            this.maximumSize = maximumSize;
        }

        public Integer getInitialCapacity() {
            return initialCapacity;
        }

        public void setInitialCapacity(Integer initialCapacity) {
            this.initialCapacity = initialCapacity;
        }

        public Integer getExpireAfterAccess() {
            return expireAfterAccess;
        }

        public void setExpireAfterAccess(Integer expireAfterAccess) {
            this.expireAfterAccess = expireAfterAccess;
        }
    }
}
