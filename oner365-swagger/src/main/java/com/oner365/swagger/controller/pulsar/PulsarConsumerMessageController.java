package com.oner365.swagger.controller.pulsar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.pulsar.IPulsarConsumerClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Pulsar - 消息服务
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "消息服务 - Pulsar")
@RequestMapping("/pulsar/message")
public class PulsarConsumerMessageController extends BaseController {

  @Autowired
  private IPulsarConsumerClient client;

  /**
   * 发送消息
   * 
   * @param message 消息
   * @return ResponseData
   */
  @ApiOperation("1.消息")
  @ApiOperationSupport(order = 1)
  @GetMapping("/send")
  public ResponseData<String> send(String message) {
    return client.send(message);
  }

}
