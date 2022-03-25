package com.oner365.pulsar.listener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oner365.pulsar.config.PulsarConfig;
import com.oner365.pulsar.config.properties.PulsarProperties;

/**
 * pulsar listener
 * 
 * @author zhaoyong
 *
 */
@Component
public class PulsarListener {

  private final Logger logger = LoggerFactory.getLogger(PulsarListener.class);

  @Autowired
  private PulsarConfig pulsarConfig;

  @Autowired
  private PulsarProperties pulsarProperties;

  @PostConstruct
  public void listener() {
    MessageListener<byte[]> messageListener = (consumer, msg) -> {
      try {
        logger.info("Message received: {}", new String(msg.getData()));
        consumer.acknowledge(msg);
      } catch (PulsarClientException e) {
        consumer.negativeAcknowledge(msg);
      }
    };
    try {
      pulsarConfig.getPulsarFactory().newConsumer().topic(pulsarProperties.getTopic())
          .subscriptionName(pulsarProperties.getSubscription()).messageListener(messageListener).subscribe();
    } catch (PulsarClientException e) {
      logger.error("listener error:", e);
    }
  }
  
  @PreDestroy
  public void destroy() {
    try {
      logger.info("Apache Pulsar close ...");
      pulsarConfig.getPulsarFactory().close();
    } catch (PulsarClientException e) {
      logger.error("listener destroy error:", e);
    }
  }
}
