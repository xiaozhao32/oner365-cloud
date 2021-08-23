package com.oner365.rocketmq.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.controller.BaseController;

/**
 * Rocketmq控制器
 * @author zhaoyong
 */
@RestController
@RequestMapping("/rocketmq")
public class RocketmqController extends BaseController {

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Autowired
    private RocketMQTemplate rocketmqTemplate;

    /**
     * 发送消息
     * @param message 消息
     * @return String
     */
    @GetMapping("/send")
    public String send(String message) {
        LOGGER.info("Send topic:{}, message:{}", topic, message);
        rocketmqTemplate.convertAndSend(topic, message);
        return message;
    }
}
