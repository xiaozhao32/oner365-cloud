package com.oner365.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 部署模块
 * 
 * @author zhaoyong
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({ "com.oner365.deploy" })
public class SpringDeployApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDeployApplication.class, args);
  }

}
