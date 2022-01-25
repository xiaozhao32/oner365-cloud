package com.oner365.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Kafka消费者接口
 *
 * @author zhaoyong
 */
public interface IKafkaConsumerService {
  
  /**
   * 监听服务
   *
   * @param record 参数
   */
  @KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = { "${spring.kafka.template.default-topic}" })
  void listen(ConsumerRecord<String, ?> record);
}
