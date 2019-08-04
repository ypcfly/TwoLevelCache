package com.ypc.spring.customcache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Author: ypcfly
 * @Date: 19-7-27 23:28
 * @Description:
 */
@Slf4j
@Configuration
public class RedisConfig {

    /**
     * 自定义redisCacheManager
     * @param redisConnectionFactory
     * @return
     */
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
//        log.info(">>>> custom redis cache manager start <<<<");
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
//
//        // 指定key value的序列化类型
//        RedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        RedisSerializationContext.SerializationPair<Object> jsonSerializationPair = RedisSerializationContext.SerializationPair
//                .fromSerializer(jsonSerializer);
//        RedisSerializationContext.SerializationPair<String> stringSerializationPair = RedisSerializationContext.SerializationPair
//                .fromSerializer(RedisSerializer.string());
//
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(stringSerializationPair)
//                .serializeValuesWith(jsonSerializationPair);
//
//        if (!customCacheProperties.isCacheNullValues()) {
//            redisCacheConfiguration.disableCachingNullValues();
//        }
//
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
//
//        return redisCacheManager;
//    }

    /**
     * 自定义redisTemplate序列化策略，注入redisTemplate时会使用该方法返回的bean
     * @param redisConnectionFactory
     * @return
     */
//    @Bean
//    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        log.info(">>>> custom redisTemplate configuration start <<<<");
//        RedisTemplate redisTemplate = new RedisTemplate();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        // 配置jackson序列化策略默认
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
//
//        // 配置key的序列化策略
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        // 设置hash的key value序列化策略
//        redisTemplate.setHashKeySerializer(RedisSerializer.string());
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        return redisTemplate;
//    }

}