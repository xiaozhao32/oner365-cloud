package com.oner365.pulsar.config;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oner365.pulsar.config.properties.PulsarProperties;

/**
 * pulsar config
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class PulsarConfig {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(PulsarConfig.class);
  
  @Autowired
  private PulsarProperties pulsarProperties;

  @Bean
  public PulsarClient getPulsarFactory() {
    PulsarClient client = null;
    try {
      client = PulsarClient.builder().serviceUrl("pulsar://" + pulsarProperties.getUrl()).build();
    } catch (PulsarClientException e) {
      LOGGER.error("PulsarClient factory error:", e);
    }
    return client;
  }
}
