package com.oner365.deploy.config.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;

/**
 * 本地部署
 * 
 * @author zhaoyong
 *
 */
@Configuration
@ConfigurationProperties(prefix = "deploy.local")
public class LocalDeployProperties {
  
  /** 部署路径 */
  private String name;
  /** 项目路径 */
  private String location;
  /** 版本 */
  private String version;
  /** 部署路径 */
  private String suffix;
  /** 部署环境 */
  private String active;
  /** 项目名称 */
  private List<String> projects;
  /** jar包 */
  private List<String> libs = new ArrayList<>();

  /**
   * 构造方法
   */
  public LocalDeployProperties() {
      super();
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getLocation() {
      return location;
  }

  public void setLocation(String location) {
      this.location = location;
  }

  public String getVersion() {
      return version;
  }

  public void setVersion(String version) {
      this.version = version;
  }

  public String getSuffix() {
      return suffix;
  }

  public void setSuffix(String suffix) {
      this.suffix = suffix;
  }

  public List<String> getProjects() {
      return projects;
  }

  public void setProjects(List<String> projects) {
      this.projects = projects;
  }

  public List<String> getLibs() {
      return libs;
  }

  public void setLibs(List<String> libs) {
      this.libs = libs;
  }
  
  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  @Override
  public String toString() {
      return JSON.toJSONString(this);
  }

}
