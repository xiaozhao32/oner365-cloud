package com.oner365.swagger.controller.mqtt;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.mqtt.IMqttConsumerClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Mqtt - 消息服务
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "消息服务 - Mqtt")
@RequestMapping("/mqtt/message")
public class MqttConsumerMessageController {

  @Resource
  private IMqttConsumerClient client;

  /**
   * 发送消息
   * 
   * @param message 消息
   * @return ResponseData
   */
  @ApiOperation("1.消息")
  @ApiOperationSupport(order = 1)
  @GetMapping("/send")
  public ResponseData<JSONObject> send(String message) {
    return client.send(message);
  }
}
