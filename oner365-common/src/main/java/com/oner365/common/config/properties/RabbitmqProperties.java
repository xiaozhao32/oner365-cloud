package com.oner365.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq配置
 * 
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitmqProperties {
  
  /**
   * 地址
   */
  private String host;
  
  /**
   * 端口
   */
  private int listener = 15672;

  /**
   * 账号
   */
  private String username;

  /**
   * 密码
   */
  private String password;
  
  public RabbitmqProperties() {
    super();
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getListener() {
    return listener;
  }

  public void setListener(int listener) {
    this.listener = listener;
  }

}
