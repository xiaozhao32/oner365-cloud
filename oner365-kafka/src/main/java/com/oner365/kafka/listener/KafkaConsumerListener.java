package com.oner365.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

/**
 * Kafka 监听服务
 *
 * @author zhaoyong
 */
@Component
public class KafkaConsumerListener {

  private final Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

  /**
   * 监听服务
   *
   * @param record 参数
   */
  @KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = { "${spring.kafka.template.default-topic}" })
  public void listen(ConsumerRecord<String, ?> record) {
    Optional<?> kafkaMessage = Optional.of(record.value());
    if (kafkaMessage.isPresent()) {
      Object message = kafkaMessage.get();
      logger.info("Kafka Message received: {}", message);
    }
  }
}
