package com.oner365.dubbo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Dubbo 消费者服务
 * @author zhaoyong
 */

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.oner365.dubbo" })
public class SpringDubboConsumerApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringDubboConsumerApplication.class, args);
    }

}
