package com.oner365.dubbo.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.dubbo.api.service.IEchoService;

/**
 * dubbo 消费者
 * @author zhaoyong
 */
@RestController
@RequestMapping("/message")
public class ConsumerController {

    @DubboReference
    private IEchoService service;

    @GetMapping("/send")
    public String send(String message) {
        return service.echo(message);
    }
}
