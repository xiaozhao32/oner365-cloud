package com.oner365.util;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * Jedis 工具类
 * 
 * @author zhaoyong
 *
 */
public class JedisUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JedisUtils.class);

  private JedisUtils() {
  }

  /**
   * 获取 Jedis
   * 
   * @param redisProperties 属性文件
   * @return Jedis
   */
  public static Jedis getJedis(RedisProperties redisProperties) {
    // sentinel
    if (redisProperties.getSentinel() != null) {
      Sentinel sentinel = redisProperties.getSentinel();
      JedisSentinelPool pool = new JedisSentinelPool(sentinel.getMaster(), new HashSet<>(sentinel.getNodes()),
          redisProperties.getPassword());
      Jedis jedis = pool.getResource();
      return getConnect(jedis, redisProperties.getPassword());
    }

    // default
    Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());
    return getConnect(jedis, redisProperties.getPassword());
  }

  private static Jedis getConnect(Jedis jedis, String password) {
    String auth = "ok";
    if (!DataUtils.isEmpty(password)) {
      auth = jedis.auth(password);
    } else {
      jedis.connect();
    }
    LOGGER.debug("info: {}", auth);
    return jedis;
  }

}
