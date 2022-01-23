package com.oner365.elasticsearch.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elasticsearch 初始化
 * 
 * @author zhaoyong
 *
 */
@Order(1)
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.oner365.elasticsearch.service")
public class ElasticsearchInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    System.setProperty("es.set.netty.runtime.available.processors", "false");
  }

}
