package com.oner365.common.cache.config;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.oner365.common.constants.PublicConstants;
import com.oner365.util.ClassesUtil;
import com.oner365.util.DataUtils;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import redis.clients.jedis.Jedis;

/**
 * Redis Cache Config
 * 
 * @author zhaoyong
 *
 */
@Configuration
@EnableCaching
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisCacheConfig implements CachingConfigurer {

  @Bean
  CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(PublicConstants.EXPIRE_TIME)))
        .transactionAware().build();
  }

  @Bean
  RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
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
  @ConditionalOnProperty(value = { "spring.redis.cluster.enable" }, havingValue = "true")
  LettuceConnectionFactory redisClusterConnectionFactory(RedisProperties redisProperties) {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(
        redisProperties.getCluster().getNodes());
    if (!DataUtils.isEmpty(redisProperties.getPassword())) {
      redisClusterConfiguration.setPassword(redisProperties.getPassword());
    }

    ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
        .enablePeriodicRefresh().enableAllAdaptiveRefreshTriggers().refreshPeriod(Duration.ofSeconds(5L)).build();
    ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
        .topologyRefreshOptions(clusterTopologyRefreshOptions).build();
    LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
        .readFrom(ReadFrom.REPLICA_PREFERRED).clientOptions(clusterClientOptions).build();
    return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
  }
  
  @Bean
  @ConditionalOnProperty(value = { "spring.redis.sentinel.enable" }, havingValue = "true")
  LettuceConnectionFactory redisSentinelConnectionFactory(RedisProperties redisProperties) {
    RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration(
        redisProperties.getSentinel().getMaster(), new HashSet<>(redisProperties.getSentinel().getNodes()));
    if (!ObjectUtils.isEmpty(redisProperties.getPassword())) {
      redisSentinelConfiguration.setPassword(redisProperties.getPassword());
    }
    GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
    LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
        .poolConfig(poolConfig).build();
    return new LettuceConnectionFactory(redisSentinelConfiguration, lettuceClientConfiguration);
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator() {
    return (target, method, params) -> {
      String sp = "::";
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
