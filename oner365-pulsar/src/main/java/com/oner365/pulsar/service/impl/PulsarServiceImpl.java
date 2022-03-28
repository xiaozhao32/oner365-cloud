package com.oner365.pulsar.service.impl;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.common.enums.ResultEnum;
import com.oner365.pulsar.config.PulsarConfig;
import com.oner365.pulsar.config.properties.PulsarProperties;
import com.oner365.pulsar.service.PulsarService;

/**
 * pulsar service impl
 * 
 * @author zhaoyong
 *
 */
@Service
public class PulsarServiceImpl implements PulsarService {

  private final Logger logger = LoggerFactory.getLogger(PulsarService.class);

  @Autowired
  private PulsarConfig pulsarConfig;

  @Autowired
  private PulsarProperties pulsarProperties;

  @Override
  public ResultEnum convertAndSend(Object message) {
    try {
      Producer<byte[]> producer = pulsarConfig.getPulsarFactory().newProducer().topic(pulsarProperties.getTopic())
          .create();
      producer.send(JSON.toJSONString(message).getBytes());
      return ResultEnum.SUCCESS;
    } catch (PulsarClientException e) {
      logger.error("convertAndSend error:", e);
    }
    return ResultEnum.ERROR;
  }

}
