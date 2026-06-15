package com.oner365.sys.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.sys.client.fallback.MonitorServiceClientFallback;

/**
 * 监控处理接口
 *
 * @author zhaoyong
 */
@FeignClient(value = "oner365-monitor", fallback = MonitorServiceClientFallback.class)
public interface IMonitorServiceClient {

    /**
     * 根据id获取任务信息
     * @param id 任务编号
     * @return ResponseData
     */
    @GetMapping(value = "/task/{id}")
    ResponseData<SysTaskDto> getInfo(@PathVariable String id);

}
