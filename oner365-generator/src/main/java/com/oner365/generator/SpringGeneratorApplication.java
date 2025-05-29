package com.oner365.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 生成框架服务
 *
 * @author zhaoyong
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan({ "com.oner365.generator.mapper" })
@ComponentScan({ "com.oner365" })
public class SpringGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGeneratorApplication.class, args);
    }

}
