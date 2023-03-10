package com.oner365.pulsar.consumer;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.oner365.pulsar.config.properties.PulsarProperties;
import com.oner365.pulsar.listener.PulsarListener;

/**
 * pulsar consumer
 * 
 * @author zhaoyong
 *
 */
@Component
public class PulsarConsumer {
  
  @Resource
  private PulsarProperties pulsarProperties;
  
  @Resource
  private PulsarClient pulsarClient;
  
  @Resource
  private PulsarListener pulsarListener;
  
  @Bean("getMessageConsumer")
  public Consumer<JSONObject> getMessageConsumer() {
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
      throw new RuntimeException("createConsumer error");
    }
  }

}
