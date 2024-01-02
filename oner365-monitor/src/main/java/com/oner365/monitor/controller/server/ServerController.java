package com.oner365.monitor.controller.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.web.controller.BaseController;
import com.oner365.monitor.entity.Server;

/**
 * 服务器监控
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/server")
public class ServerController extends BaseController {

    /**
     * 当前服务器信息
     */
    @GetMapping("/index")
    public Server index() {
        Server server = new Server();
        server.copyTo();
        return server;
    }
}
