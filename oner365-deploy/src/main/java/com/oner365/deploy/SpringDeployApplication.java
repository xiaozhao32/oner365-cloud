package com.oner365.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 部署模块
 * 
 * @author zhaoyong
 */
@SpringBootApplication
@ComponentScan({ "com.oner365" })
public class SpringDeployApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDeployApplication.class, args);
  }

}
