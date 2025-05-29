package com.oner365.hadoop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hadoop服务
 *
 * @author zhaoyong
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan({ "com.oner365" })
public class SpringHadoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringHadoopApplication.class, args);
    }

}
