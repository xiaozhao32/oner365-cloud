package com.oner365.swagger.client.monitor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.Server;

/**
 * 监控服务 - 缓存
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_MONITOR, contextId = PathConstants.CONTEXT_MONITOR_SERVER_ID)
public interface IMonitorServerClient {

    /**
     * 首页信息
     * @return ResponseData<Server>
     */
    @GetMapping(PathConstants.REQUEST_MONITOR_SERVER_INDEX)
    ResponseData<Server> index();

}
