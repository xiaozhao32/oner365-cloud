package com.oner365.pulsar.service.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.enums.ResultEnum;
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

  private final Logger logger = LoggerFactory.getLogger(PulsarServiceImpl.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Resource
  private PulsarClient pulsarClient;

  public <T> Producer<T> createProducer(String topic, Schema<T> schema) {
    try {
      return pulsarClient.newProducer(schema).topic(topic).batchingMaxPublishDelay(10, TimeUnit.MILLISECONDS)
          .sendTimeout(10, TimeUnit.SECONDS).blockIfQueueFull(true).create();
    } catch (PulsarClientException e) {
      logger.error("初始化Pulsar Producer失败", e);
    }
    return null;
  }

  @Override
  public ResultEnum convertAndSend(JSONObject message) {
    try (Producer<JSONObject> producer = createProducer(pulsarProperties.getTopic(), Schema.JSON(JSONObject.class))) {
      producer.send(message);
      return ResultEnum.SUCCESS;
    } catch (PulsarClientException e) {
      logger.error("convertAndSend error:", e);
    }
    return ResultEnum.ERROR;
  }

}
