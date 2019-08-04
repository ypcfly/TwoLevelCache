package com.ypc.spring.customcache.cache;

/**
 * @Author: ypcfly
 * @Date: 19-7-28 09:39
 * @Description: 自定义缓存类型
 */
public enum CacheType {

    NONE,
    REDIS,
    CAFFEINE,
    CAFFEINE_REDIS;

    private CacheType() {
    }
}
