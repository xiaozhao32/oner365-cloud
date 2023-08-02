package com.oner365.pulsar.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.ResponseData;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
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
   * @return ResponseData<JSONObject>
   */
  @GetMapping("/send")
  public ResponseData<JSONObject> send(String data) {
    logger.info("Pulsar send message:{}", data);
    JSONObject json = new JSONObject();
    json.put("data", data);
    ResultEnum result = pulsarService.convertAndSend(json);
    if (ResultEnum.SUCCESS.equals(result)) {
      return ResponseData.success(json);
    }
    return ResponseData.error(result.getName());
  }
}
