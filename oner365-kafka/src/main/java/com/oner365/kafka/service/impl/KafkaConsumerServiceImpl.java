package com.oner365.kafka.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oner365.kafka.service.IKafkaConsumerService;
import com.google.common.base.Optional;

/**
 * IKafkaConsumerService实现类
 * 
 * @author zhaoyong
 */
@Service
public class KafkaConsumerServiceImpl implements IKafkaConsumerService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerServiceImpl.class);

    @Override
    public void listen(ConsumerRecord<String, ?> record) {
        Optional<?> kafkaMessage = Optional.of(record.value());

        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            LOGGER.info("Receive message:{}", message);
        }
    }

}
