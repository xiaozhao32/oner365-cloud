package com.oner365.common.cache.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.oner365.common.constants.PublicConstants;
import com.oner365.util.ClassesUtil;
import com.oner365.util.DataUtils;

import redis.clients.jedis.Jedis;

/**
 * Redis Cache Config
 * @author zhaoyong
 *
 */
@Configuration  
@EnableCaching
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisCacheConfig extends CachingConfigurerSupport {  
    
    @Bean
    public CacheManager cacheManager(@Autowired RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(PublicConstants.EXPIRE_TIME)))
                .transactionAware().build();
    }
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        
        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }    
    
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            char sp = ':';
            StringBuilder strBuilder = new StringBuilder(30);
            // 类名
            strBuilder.append(target.getClass().getSimpleName());
            strBuilder.append(sp);
            // 方法名
            strBuilder.append(method.getName());
            strBuilder.append(sp);
            if (params.length > 0) {
                // 参数值
                for (Object object : params) {
                    if (DataUtils.isEmpty(object)) {
                        strBuilder.append("");
                    } else if (ClassesUtil.isPrimitive(object.getClass())) {
                        strBuilder.append(object);
                    } else {
                        strBuilder.append(JSON.toJSONString(object).hashCode());
                    }
                }
            } else {
                strBuilder.append(sp);
            }
            return strBuilder.toString();
        };
    }
    
}  
