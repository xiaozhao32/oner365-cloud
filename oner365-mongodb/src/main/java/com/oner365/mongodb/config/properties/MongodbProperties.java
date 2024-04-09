package com.oner365.mongodb.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Mongodb Properties
 * 
 * @author zhaoyong
 */
@ConfigurationProperties(MongodbProperties.PREFIX)
public class MongodbProperties {

  public static final String PREFIX = "spring.data.mongodb";

  private String database;
  private String uri;

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }
}
