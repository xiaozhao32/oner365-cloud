package com.oner365.zookeeper.config.properties;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Zookeeper properties
 * 
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "spring.cloud.zookeeper")
public class ZooKeeperProperties {

  /**
   * 连接地址
   */
  private String connectString;

  /**
   * 连接超时设置
   */
  private Duration connectionTimeout;

  /**
   * Session超时设置
   */
  private Duration sessionTimeout;

  /**
   * Sleep Time
   */
  private int baseSleepTimeMs;

  /**
   * 重试次数
   */
  private int maxRetries;
  
  public ZooKeeperProperties() {
    super();
  }

  public String getConnectString() {
    return connectString;
  }

  public void setConnectString(String connectString) {
    this.connectString = connectString;
  }

  public Duration getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(Duration connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public Duration getSessionTimeout() {
    return sessionTimeout;
  }

  public void setSessionTimeout(Duration sessionTimeout) {
    this.sessionTimeout = sessionTimeout;
  }

  public int getBaseSleepTimeMs() {
    return baseSleepTimeMs;
  }

  public void setBaseSleepTimeMs(int baseSleepTimeMs) {
    this.baseSleepTimeMs = baseSleepTimeMs;
  }

  public int getMaxRetries() {
    return maxRetries;
  }

  public void setMaxRetries(int maxRetries) {
    this.maxRetries = maxRetries;
  }

}
