package com.oner365.hadoop.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * HDFS配置类
 * 
 * @author zhaoyong
 */
@Configuration
public class HdfsProperties {

  @Value("${hdfs.path}")
  private String path;

  @Value("${hdfs.username}")
  private String username;
  
  public HdfsProperties() {
    super();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}