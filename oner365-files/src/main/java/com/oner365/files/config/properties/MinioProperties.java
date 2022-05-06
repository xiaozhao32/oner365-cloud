package com.oner365.files.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * File Minio properties
 * 
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

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
  private String url;

  /**
   * 根目录
   */
  private String bucket;

  /**
   * 构造方法
   */
  public MinioProperties() {
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
   * @return the bucket
   */
  public String getBucket() {
    return bucket;
  }

  /**
   * @param bucket the bucket to set
   */
  public void setBucket(String bucket) {
    this.bucket = bucket;
  }

}
