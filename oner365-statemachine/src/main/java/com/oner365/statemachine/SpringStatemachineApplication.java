package com.oner365.statemachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Statemachine 服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({ "com.oner365.common", "com.oner365.statemachine" })
public class SpringStatemachineApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringStatemachineApplication.class, args);
  }

}
