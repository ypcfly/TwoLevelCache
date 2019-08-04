package com.ypc.spring.customcache.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ypc.spring.customcache.properties.CustomCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ypcfly
 * @Date: 19-7-27 23:33
 * @Description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CustomCacheProperties.class})
public class CaffeineRedisCacheConfig {

    @Autowired
    private CustomCacheProperties customCacheProperties;

    /**
     * 自定义RedisCacheManger
     * @param redisConnectionFactory
     * @return
     */
    @Bean("redisCacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        log.info(">>>> create redisCacheManager bean start <<<<");
        Integer expireSecond = customCacheProperties.getExpireSecond();
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 指定key value的序列化类型
        RedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        RedisSerializationContext.SerializationPair<Object> jsonSerializationPair = RedisSerializationContext.SerializationPair
                .fromSerializer(RedisSerializer.json());
        RedisSerializationContext.SerializationPair<String> stringSerializationPair = RedisSerializationContext.SerializationPair
                .fromSerializer(RedisSerializer.string());

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(stringSerializationPair)
                .serializeValuesWith(jsonSerializationPair);

        // 设置过期时间
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(expireSecond));
        // 是否缓存null值
        if (!customCacheProperties.isCacheNullValues()) {
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);

        return redisCacheManager;
    }

    /**
     * 自定义CaffeineCacheManager
     * @return
     */
    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        log.info(">>>> create caffeineCacheManager bean start <<<<");
        Map<String,Cache<Object,Object>> map = creatCaffeineCache(customCacheProperties);
        CustomCaffeineCacheManager customCaffeineCacheManager = new CustomCaffeineCacheManager(map,customCacheProperties.isCacheNullValues());

        return customCaffeineCacheManager;

    }

    private Map<String,Cache<Object,Object>> creatCaffeineCache(CustomCacheProperties customCacheProperties) {
        Map<String,Cache<Object,Object>> map = new HashMap<>();

        Map<String,CustomCacheProperties.Container> containers = customCacheProperties.getContainers();
        for (Map.Entry entry : containers.entrySet()) {
            CustomCacheProperties.Container container = (CustomCacheProperties.Container) entry.getValue();
            Integer initialCapacity = container.getInitialCapacity();
            Integer maximumSize = container.getMaximumSize();
            Integer expireTime = container.getExpireAfterAccess();

            Caffeine<Object, Object> builder = Caffeine.newBuilder()
                    .initialCapacity(initialCapacity)
                    .maximumSize(maximumSize)
                    .expireAfterAccess(expireTime, TimeUnit.SECONDS)
                    .recordStats();

            map.put((String) entry.getKey(),builder.build());
        }

        return map;
    }

    /**
     * 自定义CaffeineRedisCacheManager
     * @param redisConnectionFactory
     * @return
     */
    @Primary
    @Bean("caffeineRedisCacheManager")
    public CacheManager caffeineRedisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        log.info(">>>> create custom caffeineRedisCacheManager bean start <<<<");
        CacheManagerContainer cacheManagerContainer = new CacheManagerContainer(redisCacheManager(redisConnectionFactory),caffeineCacheManager(),customCacheProperties);
        CacheManager cacheManager = new CaffeineRedisCacheManager(cacheManagerContainer);


        return cacheManager;
    }

    /**
     * 自定义KeyGenerator
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(method.getName());
                Object[] copy = params;
                int length = params.length;

                for(int i = 0; i < length; i++) {
                    Object object = copy[i];
                    stringBuilder.append(object.toString());
                }

                return stringBuilder.toString();
            }
        };
    }

}
