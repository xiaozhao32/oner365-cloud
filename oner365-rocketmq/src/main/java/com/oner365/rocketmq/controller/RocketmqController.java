package com.oner365.rocketmq.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.controller.BaseController;
import com.oner365.rocketmq.config.properties.RocketmqProperties;

/**
 * Rocketmq控制器
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/message")
public class RocketmqController extends BaseController {

  @Autowired
  private RocketmqProperties rocketmqProperties;

  @Autowired
  private RocketMQTemplate rocketmqTemplate;

  /**
   * 发送消息
   * 
   * @param message 消息
   * @return String
   */
  @GetMapping("/send")
  public String send(String message) {
    LOGGER.info("Send topic:{}, message:{}", rocketmqProperties.getTopic(), message);
    rocketmqTemplate.convertAndSend(rocketmqProperties.getTopic(), message);
    return message;
  }
}
