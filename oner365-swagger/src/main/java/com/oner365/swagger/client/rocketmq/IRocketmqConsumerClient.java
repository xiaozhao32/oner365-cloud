package com.oner365.swagger.client.rocketmq;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.common.ResponseData;
import com.oner365.swagger.constants.PathConstants;

/**
 * Rocketmq服务 - 消费
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_ROCKETMQ, contextId = PathConstants.CONTEXT_ROCKETMQ_CONSUMER_ID)
public interface IRocketmqConsumerClient {

  /**
   * 发送消息
   * 
   * @param message 消息
   * @return ResponseData<String>
   */
  @GetMapping(PathConstants.REQUEST_ROCKETMQ_CONSUMER_MESSAGE_SEND)
  ResponseData<String> send(@RequestParam(value = "message") String message);
}
