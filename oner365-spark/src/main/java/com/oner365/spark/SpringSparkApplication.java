package com.oner365.spark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spark 服务
 *
 * @author zhaoyong
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan({ "com.oner365" })
public class SpringSparkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSparkApplication.class, args);
    }

}
