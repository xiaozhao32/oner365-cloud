package com.oner365.influx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Pulsar 服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({ "com.oner365.common", "com.oner365.influx" })
public class SpringInfluxApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringInfluxApplication.class, args);
  }

}
