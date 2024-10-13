package com.oner365.kafka.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.kafka.common.serialization.Serdes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.kafka.config.properties.KafkaProperties;
import com.oner365.kafka.constants.KafkaConstants;

/**
 * Kafka Config
 * 
 * @author zhaoyong
 */
@Configuration
@EnableKafkaStreams
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig {

  @Resource
  private KafkaProperties kafkaProperties;

  /**
   * KafkaStreams Config
   * 
   * @return KafkaStreamsConfiguration
   */
  @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
  KafkaStreamsConfiguration kafkaStreamsConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(KafkaConstants.APPLICATION_ID_CONFIG, PublicConstants.NAME);
    props.put(KafkaConstants.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    props.put(KafkaConstants.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(KafkaConstants.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    return new KafkaStreamsConfiguration(props);
  }
}
