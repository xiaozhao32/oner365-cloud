package com.oner365.pulsar.listener;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.oner365.pulsar.config.properties.PulsarProperties;

/**
 * pulsar listener
 * 
 * @author zhaoyong
 *
 */
@Component
public class PulsarListener implements MessageListener<JSONObject> {
  
  private static final long serialVersionUID = 1L;

  private final Logger logger = LoggerFactory.getLogger(PulsarListener.class);

  @Resource
  private PulsarProperties pulsarProperties;

  @Resource
  private PulsarClient pulsarClient;

  @Override
  public void received(Consumer<JSONObject> consumer, Message<JSONObject> msg) {
    try {
      logger.info("Pulsar data: {}, topic: {}", new String(msg.getData()), consumer.getTopic());
      consumer.acknowledge(msg);
    } catch (PulsarClientException e) {
      consumer.negativeAcknowledge(msg);
    }
  }
  
}
