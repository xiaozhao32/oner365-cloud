package com.oner365.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Datasource 服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({ "com.oner365" })
public class SpringDatasourceApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDatasourceApplication.class, args);
  }

}
