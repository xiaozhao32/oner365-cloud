package com.oner365.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Kafka服务
 * @author zhaoyong
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({ "com.oner365.common", "com.oner365.kafka" })
public class SpringKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }

}
