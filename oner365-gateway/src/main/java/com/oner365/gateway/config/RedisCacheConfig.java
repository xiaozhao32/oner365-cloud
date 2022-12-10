package com.oner365.gateway.config;

import java.io.Serializable;
import java.time.Duration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.oner365.gateway.constants.GatewayConstants;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import redis.clients.jedis.Jedis;

/**
 * Redis Cache Config
 * 
 * @author liutao
 *
 */
@Configuration
@EnableCaching
@ConditionalOnClass({ JedisConnection.class, RedisOperations.class, Jedis.class })
@EnableConfigurationProperties(RedisProperties.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisCacheConfig {

  @Bean
  CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(GatewayConstants.EXPIRE_TIME)))
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
  LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(
        redisProperties.getCluster().getNodes());
    if (!ObjectUtils.isEmpty(redisProperties.getPassword())) {
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

}
