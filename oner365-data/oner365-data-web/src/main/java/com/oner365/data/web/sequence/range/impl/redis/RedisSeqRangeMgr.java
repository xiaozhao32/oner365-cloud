package com.oner365.data.web.sequence.range.impl.redis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import com.oner365.data.redis.util.JedisUtils;
import com.oner365.data.web.sequence.range.SeqRange;
import com.oner365.data.web.sequence.range.SeqRangeMgr;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * sequence redis range
 *
 * @author zhaoyong
 */
public class RedisSeqRangeMgr implements SeqRangeMgr {

    private static final String KEY_PREFIX = "sequence_";

    private int step = 1000;

    private long stepStart = 0L;

    private volatile boolean keyAlreadyExist;

    private RedisProperties properties;

    @Override
    public SeqRange nextRange(String name) {
        if (this.properties.getCluster() != null) {
            Set<HostAndPort> nodes = new HashSet<>();
            this.properties.getCluster().getNodes().forEach(s -> {
                HostAndPort host = HostAndPort.from(s);
                nodes.add(host);
            });
            try (JedisCluster cluster = new JedisCluster(nodes, null, this.properties.getPassword())) {
                if (!this.keyAlreadyExist) {
                    Boolean isExists = cluster.exists(getRealKey(name));
                    if (Boolean.FALSE.equals(isExists)) {
                        cluster.setnx(getRealKey(name), String.valueOf(this.stepStart));
                    }
                    this.keyAlreadyExist = true;
                }
                long max = cluster.incrBy(getRealKey(name), this.step);
                long min = max - this.step + 1L;
                return new SeqRange(min, max);
            }
        }
        else {
            try (Jedis jedis = JedisUtils.getJedis(this.properties)) {
                if (!this.keyAlreadyExist) {
                    Boolean isExists = jedis.exists(getRealKey(name));
                    if (Boolean.FALSE.equals(isExists)) {
                        jedis.setnx(getRealKey(name), String.valueOf(this.stepStart));
                    }
                    this.keyAlreadyExist = true;
                }
                long max = jedis.incrBy(getRealKey(name), this.step);
                long min = max - this.step + 1L;
                return new SeqRange(min, max);
            }
        }
    }

    @Override
    public void init() {
        // init config
    }

    private String getRealKey(String name) {
        return KEY_PREFIX + name;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getStepStart() {
        return this.stepStart;
    }

    public void setStepStart(long stepStart) {
        this.stepStart = stepStart;
    }

    public RedisProperties getProperties() {
        return properties;
    }

    public void setProperties(RedisProperties properties) {
        this.properties = properties;
    }

}
