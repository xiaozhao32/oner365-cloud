package com.oner365.files.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * File Minio properties
 * 
 * @author zhaoyong
 */
@Configuration
@ConfigurationProperties(prefix = "local")
public class FileLocalProperties {

  /**
   * 本地web地址
   */
  private String web;

  /**
   * 本地上传地址
   */
  private String upload;

  /**
   * 构造方法
   */
  public FileLocalProperties() {
    super();
  }

  /**
   * @return the web
   */
  public String getWeb() {
    return web;
  }

  /**
   * @param web the web to set
   */
  public void setWeb(String web) {
    this.web = web;
  }

  /**
   * @return the upload
   */
  public String getUpload() {
    return upload;
  }

  /**
   * @param upload the upload to set
   */
  public void setUpload(String upload) {
    this.upload = upload;
  }

}
