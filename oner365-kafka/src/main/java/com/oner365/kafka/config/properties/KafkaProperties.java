package com.oner365.kafka.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Kafka配置类
 * 
 * @author zhaoyong
 */
@Configuration
public class KafkaProperties {

  /**
   * topic
   */
  @Value("${spring.kafka.template.default-topic}")
  private String topic;
  
  /**
   * group
   */
  @Value("${spring.kafka.consumer.group-id}")
  private String group;
  
  public KafkaProperties() {
    super();
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }
  
}
