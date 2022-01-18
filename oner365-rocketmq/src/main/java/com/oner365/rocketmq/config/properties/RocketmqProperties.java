package com.oner365.rocketmq.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Rocketmq配置类
 * 
 * @author zhaoyong
 */
@Configuration
public class RocketmqProperties {

  @Value("${rocketmq.consumer.topic}")
  private String topic;
  
  @Value("${rocketmq.producer.group}")
  private String group;
  
  public RocketmqProperties() {
    super();
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }
  
}
