package com.oner365.swagger.controller.monitor.server;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.monitor.IMonitorServerClient;
import com.oner365.swagger.dto.Server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 服务器监控
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "监控 - 服务器")
@RequestMapping("/server")
public class MonitorServerController {

    @Resource
    private IMonitorServerClient client;

    /**
     * 当前服务器信息
     */
    @ApiOperation("1.首页")
    @ApiOperationSupport(order = 1)
    @GetMapping("/index")
    public ResponseData<Server> index() {
        return client.index();
    }

}
