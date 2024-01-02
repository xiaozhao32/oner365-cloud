package com.oner365.swagger.client.kafka;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.constants.PathConstants;

/**
 * Kafka服务 - 消费
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_KAFKA, contextId = PathConstants.CONTEXT_KAFKA_CONSUMER_ID)
public interface IKafkaConsumerClient {

  /**
   * 发送消息
   * 
   * @param message 消息
   * @return ResponseData<String>
   */
  @GetMapping(PathConstants.REQUEST_KAFKA_CONSUMER_MESSAGE_SEND)
  ResponseData<String> send(@RequestParam(value = "message") String message);
}
