package com.oner365.monitor.controller.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseData;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.monitor.dto.CacheCommandStatsDto;
import com.oner365.monitor.dto.CacheInfoDto;
import com.oner365.monitor.dto.CacheJedisInfoDto;
import com.oner365.util.DataUtils;

import redis.clients.jedis.Jedis;

/**
 * 缓存监控
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/cache")
public class CacheController extends BaseController {

  private static final int DB_LENGTH = 15;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;
  
  @Autowired
  private RedisProperties redisProperties;

  /**
   * 缓存信息
   * 
   * @return CacheInfoDto
   */
  @GetMapping("/index")
  public CacheInfoDto index() {
    Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::info);
    Properties commandStats = (Properties) redisTemplate
            .execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
    Long dbSize = (Long) redisTemplate.execute((RedisCallback<Object>) RedisServerCommands::dbSize);

    CacheInfoDto result = new CacheInfoDto();
    result.setInfo(info);
    result.setDbSize(dbSize);

    List<CacheCommandStatsDto> cacheCommandStatsDtoList = new ArrayList<>();
    if (commandStats != null) {
      commandStats.stringPropertyNames().forEach(key -> {
        CacheCommandStatsDto data = new CacheCommandStatsDto();
        String property = commandStats.getProperty(key);
        data.setName(StringUtils.removeStart(key, "cmdstat_"));
        data.setValue(StringUtils.substringBetween(property, "calls=", ",usec"));
        cacheCommandStatsDtoList.add(data);
      });
    }
    result.setCommandStats(cacheCommandStatsDtoList);
    return result;
  }

  /**
   * 缓存列表
   * 
   * @return List<CacheJedisInfoDto>
   */
  @GetMapping("/list")
  public List<CacheJedisInfoDto> cacheList() {
    List<CacheJedisInfoDto> result = new ArrayList<>();
    try (Jedis jedis = getJedis()) {
      if (jedis.isConnected()) {
        IntStream.range(0, DB_LENGTH).forEach(i -> {
          jedis.select(i);
          Long size = jedis.dbSize();
          if (size != 0L) {
            CacheJedisInfoDto dto = new CacheJedisInfoDto();
            dto.setName("DB" + i);
            dto.setIndex(i);
            dto.setSize(size);
            result.add(dto);
          }
        });
      }
    }
    return result;
  }

  /**
   * 清理缓存
   * 
   * @param index db
   * @return String
   */
  @GetMapping("/clean")
  public ResponseData<String> clean(int index) {
    try (Jedis jedis = getJedis()) {
      if (jedis.isConnected()) {
        jedis.select(index);
        jedis.flushDB();
      }
    }
    return ResponseData.success(ResultEnum.SUCCESS.getName());
  }
  
  private Jedis getJedis() {
    Jedis jedis = new Jedis(redisProperties.getHost(), redisProperties.getPort());

    String auth = "ok";
    if (!DataUtils.isEmpty(redisProperties.getPassword())) {
      auth = jedis.auth(redisProperties.getPassword());
    } else {
      jedis.connect();
    }
    logger.debug("info: {}", auth);
    return jedis;
  }

}
