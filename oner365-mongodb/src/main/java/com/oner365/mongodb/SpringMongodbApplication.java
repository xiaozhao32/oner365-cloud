package com.oner365.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Mongodb 服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({ "com.oner365" })
public class SpringMongodbApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringMongodbApplication.class, args);
  }

}
