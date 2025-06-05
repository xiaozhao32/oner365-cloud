package com.oner365.swagger.client.monitor;

import java.io.Serializable;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.enums.RabbitmqTypeEnum;

/**
 * 监控服务 - 队列
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_MONITOR, contextId = PathConstants.CONTEXT_MONITOR_RABBITMQ_ID)
public interface IMonitorRabbitmqClient {

    /**
     * 首页信息
     * @return ResponseData<Serializable>
     */
    @GetMapping(PathConstants.REQUEST_MONITOR_RABBITMQ_INDEX)
    ResponseData<Serializable> index();

    /**
     * 获取队列列表
     * @param type 类型
     * @param pageIndex 分页页面
     * @param pageSize 分页长度
     * @param name 查询名称
     * @return ResponseData<Serializable>
     */
    @GetMapping(PathConstants.REQUEST_MONITOR_RABBITMQ_LIST)
    ResponseData<Serializable> list(@PathVariable("type") RabbitmqTypeEnum type, @RequestParam("pageIndex") int pageIndex,
            @RequestParam("pageSize") int pageSize, @RequestParam("name") String name);

    /**
     * 删除
     * @param type 类型
     * @param name 查询名称
     * @return ResponseData<Serializable>
     */
    @DeleteMapping(PathConstants.REQUEST_MONITOR_RABBITMQ_DELETE)
    ResponseData<Serializable> delete(@PathVariable("type") String type, @PathVariable("name") String name);

}
