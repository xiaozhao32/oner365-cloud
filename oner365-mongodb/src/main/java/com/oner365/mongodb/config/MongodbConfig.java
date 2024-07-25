package com.oner365.mongodb.config;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.oner365.mongodb.config.properties.MongodbProperties;

/**
 * Mongodb Config
 * 
 * @author zhaoyong
 */
@Configuration
@EnableConfigurationProperties(MongodbProperties.class)
@EnableMongoRepositories(basePackages = "com.oner365.mongodb.repository")
public class MongodbConfig {
  
  @Resource
  private MongodbProperties properties;

  @Bean
  MongoClient mongoClient() {
    return MongoClients.create(properties.getUri());
  }

  @Bean
  MongoDatabaseFactory mongoDatabaseFactory() {
    return new SimpleMongoClientDatabaseFactory(MongoClients.create(), properties.getDatabase());
  }

}
