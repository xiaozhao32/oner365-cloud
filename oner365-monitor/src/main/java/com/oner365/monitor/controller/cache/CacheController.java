package com.oner365.monitor.controller.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.redis.util.JedisUtils;
import com.oner365.data.web.controller.BaseController;
import com.oner365.monitor.dto.CacheCommandStatsDto;
import com.oner365.monitor.dto.CacheInfoDto;
import com.oner365.monitor.dto.CacheJedisInfoDto;

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

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private RedisProperties redisProperties;

    /**
     * 缓存信息
     * @return CacheInfoDto
     */
    @GetMapping("/index")
    public CacheInfoDto index() {
        Properties info = redisTemplate.execute((RedisCallback<Properties>) RedisServerCommands::info);
        Properties commandStats = redisTemplate
            .execute((RedisCallback<Properties>) connection -> connection.info("commandstats"));
        Long dbSize = redisTemplate.execute(RedisServerCommands::dbSize);

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
     * @return List<CacheJedisInfoDto>
     */
    @GetMapping("/list")
    public List<CacheJedisInfoDto> cacheList() {
        List<CacheJedisInfoDto> result = new ArrayList<>();
        if (redisProperties.getCluster() != null) {
            CacheJedisInfoDto dto = new CacheJedisInfoDto();
            dto.setName("Cluster");
            dto.setIndex(0);
            dto.setSize(redisTemplate.execute(RedisServerCommands::dbSize));
            result.add(dto);
        }
        else {
            try (Jedis jedis = JedisUtils.getJedis(redisProperties)) {
                if (jedis.isConnected()) {
                    IntStream.range(0, DB_LENGTH).forEach(i -> {
                        jedis.select(i);
                        if (jedis.dbSize() != 0L) {
                            CacheJedisInfoDto dto = new CacheJedisInfoDto();
                            dto.setName("DB" + i);
                            dto.setIndex(i);
                            dto.setSize(jedis.dbSize());
                            result.add(dto);
                        }
                    });
                }
            }
        }
        return result;
    }

    /**
     * 清理缓存
     * @param index db
     * @return String
     */
    @GetMapping("/clean")
    public String clean(int index) {
        if (redisProperties.getCluster() != null) {
            redisTemplate.execute((RedisCallback<Properties>) connection -> {
                connection.serverCommands().flushAll();
                return null;
            });
        }
        else {
            try (Jedis jedis = JedisUtils.getJedis(redisProperties)) {
                if (jedis.isConnected()) {
                    jedis.select(index);
                    jedis.flushDB();
                }
            }
        }
        return ResultEnum.SUCCESS.getName();
    }

}
