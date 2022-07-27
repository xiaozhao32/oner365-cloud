package com.oner365.kafka.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.oner365.common.enums.ResultEnum;
import com.oner365.kafka.config.properties.KafkaProperties;
import com.oner365.kafka.service.IKafkaConsumerService;

import javax.annotation.Resource;

/**
 * IKafkaConsumerService实现类
 *
 * @author zhaoyong
 */
@Service
public class KafkaConsumerServiceImpl implements IKafkaConsumerService {

  private final Logger logger = LoggerFactory.getLogger(IKafkaConsumerService.class);

  @Resource
  private KafkaProperties kafkaProperties;

  @Resource
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
