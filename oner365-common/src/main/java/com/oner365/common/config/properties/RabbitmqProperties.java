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
   * 账号
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * web地址
   */
  private String uri;

  /**
   * virtual host
   */
  private String virtualHost;

  public RabbitmqProperties() {
    super();
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

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * @return the virtualHost
   */
  public String getVirtualHost() {
    return virtualHost;
  }

  /**
   * @param virtualHost the virtualHost to set
   */
  public void setVirtualHost(String virtualHost) {
    this.virtualHost = virtualHost;
  }

}
