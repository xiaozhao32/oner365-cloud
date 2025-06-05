package com.oner365.swagger.client.monitor;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.CacheInfoDto;
import com.oner365.swagger.dto.CacheJedisInfoDto;

/**
 * 监控服务 - 缓存
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_MONITOR, contextId = PathConstants.CONTEXT_MONITOR_CACHE_ID)
public interface IMonitorCacheClient {

    /**
     * 缓存信息
     * @return ResponseData<CacheInfoDto>
     */
    @GetMapping(PathConstants.REQUEST_MONITOR_CACHE_INDEX)
    ResponseData<CacheInfoDto> index();

    /**
     * 缓存列表
     * @return ResponseData<List<CacheJedisInfoDto>>
     */
    @GetMapping(PathConstants.REQUEST_MONITOR_CACHE_LIST)
    ResponseData<List<CacheJedisInfoDto>> list();

    /**
     * 清除缓存
     * @param index 所属db
     * @return ResponseData<String>
     */
    @GetMapping(PathConstants.REQUEST_MONITOR_CACHE_CLEAN)
    ResponseData<String> clean(@RequestParam("index") int index);

}
