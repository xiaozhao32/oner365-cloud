package com.oner365.common.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Token相关配置
 * 
 * @author zhaoyong
 */
@Configuration
public class AccessTokenProperties {
  
  /**
   * token密钥
   */
  @Value("${ACCESS_TOKEN_SECRET}")
  private String accessTokenSecret;

  /**
   * token过期分钟
   */
  @Value("${ACCESS_TOKEN_EXPIRY_MIN}")
  private int accessTokenExpireTime;
  
  /**
   * 构造方法
   */
  public AccessTokenProperties() {
    super();
  }

  public String getAccessTokenSecret() {
    return accessTokenSecret;
  }

  public void setAccessTokenSecret(String accessTokenSecret) {
    this.accessTokenSecret = accessTokenSecret;
  }

  public int getAccessTokenExpireTime() {
    return accessTokenExpireTime;
  }

  public void setAccessTokenExpireTime(int accessTokenExpireTime) {
    this.accessTokenExpireTime = accessTokenExpireTime;
  }
  
}
