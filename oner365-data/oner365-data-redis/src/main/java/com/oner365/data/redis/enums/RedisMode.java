package com.oner365.data.redis.enums;

/**
 * RedisConnectionFactory Mode
 *
 * @author zhaoyong
 */
public enum RedisMode {

    /**
     * 默认连接类型
     */
    DEFAULT,

    /**
     * Redis Cluster
     */
    CLUSTER,

    /**
     * Redis Sentinel
     */
    SENTINEL;

}
