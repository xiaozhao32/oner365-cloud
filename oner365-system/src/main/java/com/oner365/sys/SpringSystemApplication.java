package com.oner365.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统服务
 * @author zhaoyong
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan({ "com.oner365.sys.mapper" })
@ComponentScan({ "com.oner365" })
public class SpringSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSystemApplication.class, args);
    }
    
}
