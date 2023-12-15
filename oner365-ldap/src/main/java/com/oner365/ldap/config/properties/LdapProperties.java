package com.oner365.ldap.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Ldap Properties
 * 
 * @author zhaoyong
 */
@ConfigurationProperties(LdapProperties.PREFIX)
public class LdapProperties {

  public static final String PREFIX = "spring.ldap";

  private String urls;

  private String base;

  private String username;

  private String password;

  public String getUrls() {
    return urls;
  }

  public void setUrls(String urls) {
    this.urls = urls;
  }

  public String getBase() {
    return base;
  }

  public void setBase(String base) {
    this.base = base;
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
}
