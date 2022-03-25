package com.oner365.pulsar.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * pulsar 相关配置
 * 
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "pulsar")
public class PulsarProperties {
  
  /**
   * 请求地址
   */
  private String url;
  
  /**
   * 队列
   */
  private String topic;
  
  /**
   * subscription
   */
  private String subscription;
  
  public PulsarProperties() {
    super();
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * @param topic the topic to set
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * @return the subscription
   */
  public String getSubscription() {
    return subscription;
  }

  /**
   * @param subscription the subscription to set
   */
  public void setSubscription(String subscription) {
    this.subscription = subscription;
  }

}
