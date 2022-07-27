package com.oner365.pulsar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @Autowired
  private PulsarService pulsarService;

  /**
   * 测试发送
   *
   * @param message 参数
   * @return ResponseData<String>
   */
  @GetMapping("/send")
  public ResponseData<String> send(String message) {
    logger.info("Pulsar send message:{}", message);
    ResultEnum result = pulsarService.convertAndSend(message);
    if (ResultEnum.SUCCESS.equals(result)) {
      return ResponseData.success(message);
    }
    return ResponseData.error(result.getName());
  }
}
