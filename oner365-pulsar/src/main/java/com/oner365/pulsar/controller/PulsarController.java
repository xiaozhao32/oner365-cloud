package com.oner365.pulsar.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;
import com.oner365.pulsar.service.PulsarService;

/**
 * pulsar controller
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/message")
public class PulsarController extends BaseController {

  @Resource
  private PulsarService pulsarService;

  /**
   * 测试发送
   *
   * @param data 参数
   * @return ResponseData<String>
   */
  @GetMapping("/send")
  public ResponseData<String> send(String message) {
    logger.info("Pulsar send message:{}", message);
    JSONObject json = new JSONObject();
    json.put("message", message);
    ResultEnum result = pulsarService.convertAndSend(json);
    if (ResultEnum.SUCCESS.equals(result)) {
      return ResponseData.success(message);
    }
    return ResponseData.error(result.getName());
  }
}
