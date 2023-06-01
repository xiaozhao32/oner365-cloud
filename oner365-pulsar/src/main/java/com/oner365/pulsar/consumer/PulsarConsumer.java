package com.oner365.pulsar.consumer;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;
import com.oner365.pulsar.config.properties.PulsarProperties;
import com.oner365.pulsar.listener.PulsarListener;

/**
 * pulsar consumer
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class PulsarConsumer {
  
  private final Logger logger = LoggerFactory.getLogger(PulsarConsumer.class);
  
  @Resource
  private PulsarProperties pulsarProperties;
  
  @Resource
  private PulsarClient pulsarClient;
  
  @Resource
  private PulsarListener pulsarListener;
  
  @Bean("getMessageConsumer")
  Consumer<JSONObject> getMessageConsumer() {
    return createConsumer(pulsarProperties.getTopic(), pulsarProperties.getSubscription(),
        pulsarListener, Schema.JSON(JSONObject.class));
  }
  
  public <T> Consumer<T> createConsumer(String topic, String subscription, MessageListener<T> messageListener,
      Schema<T> schema) {
    try {
      return pulsarClient.newConsumer(schema).topic(topic).subscriptionName(subscription)
          .ackTimeout(10, TimeUnit.SECONDS).subscriptionType(SubscriptionType.Shared).messageListener(messageListener)
          .subscribe();
    } catch (PulsarClientException e) {
      logger.error("createConsumer error", e);
    }
    return null;
  }

}
