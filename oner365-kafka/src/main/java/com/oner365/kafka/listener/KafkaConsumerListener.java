package com.oner365.kafka.listener;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.oner365.kafka.constants.KafkaConstants;

/**
 * Kafka 监听服务
 *
 * @author zhaoyong
 */
@Component
public class KafkaConsumerListener {

  private final Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

  /**
   * 监听 Topic1 服务
   *
   * @param record 参数
   */
  @KafkaListener(id = "${spring.kafka.consumer.group-id}", topics = { KafkaConstants.TOPIC_1 })
  public void listen(ConsumerRecord<String, ?> consumerRecord) {
    Optional<?> kafkaMessage = Optional.of(consumerRecord.value());
    Object message = kafkaMessage.get();
    logger.info("Kafka Message received: {}", message);
  }
}
