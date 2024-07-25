package com.oner365.data.commons.cache;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.oner365.data.commons.util.DataUtils;

/**
 * Guava cache
 *
 * @author zhaoyong
 *
 */
@Component
public class GuavaCache<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaCache.class);

    /** 设置并发级别，并发级别是指可以同时写缓存的线程数 */
    private static final int CONCURRENCY_LEVEL = 10;

    /** 设置缓存容器的初始容量 */
    private static final int INITIAL_CAPACITY = 10;

    /** 设置缓存最大容量为1000，超过1000之后就会按照LRU最近最少使用算法来移除缓存项 */
    private static final int MAXIMUM_SIZE = 1000;

    /** 设置写缓存过期时间 */
    private static final int EXPIRE_AFTER_WRITE = 1;

    /** 创建缓存 */
    private final LoadingCache<String, Optional<T>> cache = CacheBuilder.newBuilder()
            .concurrencyLevel(CONCURRENCY_LEVEL)
            .expireAfterWrite(EXPIRE_AFTER_WRITE, TimeUnit.MINUTES)
            .initialCapacity(INITIAL_CAPACITY)
            .maximumSize(MAXIMUM_SIZE)
            .recordStats()
            .build(new CacheLoader<String, Optional<T>>() {
                @Override
                public Optional<T> load(@NonNull String key) {
                    LOGGER.debug("load: {}", key);
                    return Optional.empty();
                }
                @Override
                public ListenableFuture<Optional<T>> reload(@NonNull String key, @NonNull Optional<T> value) {
                    LOGGER.debug("reload: {}, value: {}", key, value);
                    return Futures.immediateFuture(load(key));
                }
            });

    /**
     * 构造方法
     */
    public GuavaCache() {
        super();
    }

    /**
     * 获取缓存
     * @param key 键
     * @return Optional<T>
     */
    public Optional<T> getCache(String key) {
      if (DataUtils.isEmpty(key)) {
      return Optional.empty();
    }
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            LOGGER.error("getCache error: {}", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     */
    public void setCache(String key, Optional<T> value) {
      if (!DataUtils.isEmpty(key)) {
          cache.put(key, value);
      }
    }

    /**
     * 清除缓存
     * @param key 键
     */
    public void removeCache(String key) {
      if (!DataUtils.isEmpty(key)) {
          cache.invalidate(key);
      }
    }

    /**
     * 清除所有缓存
     */
    public void removeAll() {
        cache.invalidateAll();
    }

}
