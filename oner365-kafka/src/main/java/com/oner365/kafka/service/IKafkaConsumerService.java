package com.oner365.kafka.service;

import com.oner365.common.enums.ResultEnum;

/**
 * Kafka消费者接口
 *
 * @author zhaoyong
 */
public interface IKafkaConsumerService {
  
  /**
   * 发送消息
   * 
   * @param message 消息
   * @return 发送结果
   */
  ResultEnum convertAndSend(Object message);
}
