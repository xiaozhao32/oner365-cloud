package com.oner365.kafka.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.oner365.common.enums.ResultEnum;
import com.oner365.kafka.config.properties.KafkaProperties;
import com.oner365.kafka.service.IKafkaConsumerService;

/**
 * IKafkaConsumerService实现类
 * 
 * @author zhaoyong
 */
@Service
public class KafkaConsumerServiceImpl implements IKafkaConsumerService {

  private final Logger logger = LoggerFactory.getLogger(IKafkaConsumerService.class);

  @Autowired
  private KafkaProperties kafkaProperties;

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public ResultEnum convertAndSend(Object message) {
    try {
      kafkaTemplate.send(kafkaProperties.getTopic(), message);
      return ResultEnum.SUCCESS;
    } catch (Exception e) {
      logger.error("convertAndSend error:", e);
    }
    return ResultEnum.ERROR;
  }

}
