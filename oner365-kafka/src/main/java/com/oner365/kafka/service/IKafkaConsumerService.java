package com.oner365.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import com.oner365.kafka.constants.KafkaConstants;

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
    @KafkaListener(id = KafkaConstants.GROUP_ID, topics = { KafkaConstants.TOPIC })
    void listen(ConsumerRecord<String, ?> record);
}
