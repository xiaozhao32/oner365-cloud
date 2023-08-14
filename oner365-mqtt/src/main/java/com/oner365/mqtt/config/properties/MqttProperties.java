package com.oner365.mqtt.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT properties
 * 
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

  /**
   * 账号
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 地址
   */
  private String uri;

  /**
   * client
   */
  private String clientId;

  /**
   * 构造方法
   */
  public MqttProperties() {
    super();
  }

  /**
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the uri
   */
  public String getUri() {
    return uri;
  }

  /**
   * @param uri the uri to set
   */
  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * @return the clientId
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * @param clientId the clientId to set
   */
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

}
