package com.oner365.elasticsearch.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Token相关配置
 * 
 * @author zhaoyong
 */
@Configuration
public class ElasticsearchProperties {
  
  /**
   * elasticsearch地址
   */
  @Value("${spring.elasticsearch.uris}")
  private String uris;
  
  /**
   * 构造方法
   */
  public ElasticsearchProperties() {
    super();
  }

  public String getUris() {
    return uris;
  }

  public void setUris(String uris) {
    this.uris = uris;
  }

}
