package com.oner365.pulsar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.controller.BaseController;
import com.oner365.pulsar.service.PulsarService;

/**
 * pulsar controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/pulsar")
public class PulsarController extends BaseController {

  @Autowired
  private PulsarService pulsarService;

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
    pulsarService.convertAndSend(data);
    return json;
  }
}
