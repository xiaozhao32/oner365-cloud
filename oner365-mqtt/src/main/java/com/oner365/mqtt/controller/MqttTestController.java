package com.oner365.mqtt.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.data.web.controller.BaseController;
import com.oner365.mqtt.service.IMqttSendMessageService;

/**
 * 队列 controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/message")
public class MqttTestController extends BaseController {

  @Resource
  private IMqttSendMessageService service;

  /**
   * 测试发送
   * 
   * @param data 参数
   * @return JSONObject
   */
  @GetMapping("/send")
  public JSONObject send(String data) {
    JSONObject json = new JSONObject();
    json.put("data", data);
    service.sendMessage(json.toJSONString());
    return json;
  }
}
