package com.oner365.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Token相关配置
 * 
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "token")
public class AccessTokenProperties {

  /**
   * token密钥
   */
  private String secret;

  /**
   * token过期分钟
   */
  private int expireTime;

  /**
   * 构造方法
   */
  public AccessTokenProperties() {
    super();
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public int getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(int expireTime) {
    this.expireTime = expireTime;
  }

}
