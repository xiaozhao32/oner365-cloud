package com.oner365.pulsar.config;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({ PulsarProperties.class })
public class PulsarConfig {

  private final Logger logger = LoggerFactory.getLogger(PulsarConfig.class);

  @Resource
  private PulsarProperties pulsarProperties;
  
  @Bean
  PulsarClient getPulsarFactory() {
    PulsarClient client = null;
    try {
      client = PulsarClient.builder().serviceUrl("pulsar://" + pulsarProperties.getUrl()).build();
    } catch (PulsarClientException e) {
      logger.error("PulsarClient factory error:", e);
    }
    return client;
  }
  
  @PreDestroy
  public void destroy() {
    try {
      logger.info("Destroy Pulsar factory.");
      getPulsarFactory().close();
    } catch (PulsarClientException e) {
      logger.error("Destroy Pulsar error:", e);
    }
  }
}
