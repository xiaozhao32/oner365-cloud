package com.oner365.rocketmq.service.impl;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.oner365.common.enums.ResultEnum;
import com.oner365.rocketmq.config.properties.RocketmqProperties;
import com.oner365.rocketmq.service.RocketmqService;

@Service
public class RocketmqServiceImpl implements RocketmqService {

  private final Logger logger = LoggerFactory.getLogger(RocketmqService.class);

  @Autowired
  private RocketmqProperties rocketmqProperties;

  @Autowired
  private RocketMQTemplate rocketmqTemplate;

  @Override
  public ResultEnum convertAndSend(Object message) {
    try {
      rocketmqTemplate.convertAndSend(rocketmqProperties.getTopic(), message);
      return ResultEnum.SUCCESS;
    } catch (MessagingException e) {
      logger.error("convertAndSend error:", e);
    }
    return ResultEnum.ERROR;
  }

}
