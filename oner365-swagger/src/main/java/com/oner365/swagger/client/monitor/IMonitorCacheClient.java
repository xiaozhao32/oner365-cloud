package com.oner365.swagger.client.monitor;

import org.springframework.cloud.openfeign.FeignClient;

import com.oner365.swagger.constants.PathConstants;

/**
 * 监控服务 - 缓存
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_MONITOR, contextId = PathConstants.CONTEXT_MONITOR_CACHE_ID)
public interface IMonitorCacheClient {

}
