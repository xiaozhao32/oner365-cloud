package com.oner365.swagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Swagger服务
 * 
 * @author zhaoyong
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({ "com.oner365.common", "com.oner365.swagger", "com.oner365.api" })
public class SpringSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSwaggerApplication.class, args);
    }

}
