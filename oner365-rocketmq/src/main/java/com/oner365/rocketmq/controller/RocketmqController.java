package com.oner365.rocketmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseData;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.rocketmq.service.RocketmqService;

/**
 * Rocketmq控制器
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/message")
public class RocketmqController extends BaseController {

  @Autowired
  private RocketmqService service;

  /**
   * 发送消息
   * 
   * @param message 消息
   * @return String
   */
  @GetMapping("/send")
  public ResponseData<String> send(String message) {
    logger.info("Rocketmq send message:{}", message);
    ResultEnum result = service.convertAndSend(message);
    if (ResultEnum.SUCCESS.equals(result)) {
      return ResponseData.success(message);
    }
    return ResponseData.error(result.getName());
  }
}
