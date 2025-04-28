package com.oner365.activemq.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.activemq.service.SendMessageService;
import com.oner365.data.web.controller.BaseController;

/**
 * 队列 controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/message")
public class TestController extends BaseController {

  @Resource
  private SendMessageService service;

  /**
   * 测试发送
   * 
   * @param message 参数
   * @return JSONObject
   */
  @GetMapping("/send")
  public JSONObject send(String message) {
    service.sendMessage(message);
    JSONObject json = new JSONObject();
    json.put("message", message);
    return json;
  }
}
