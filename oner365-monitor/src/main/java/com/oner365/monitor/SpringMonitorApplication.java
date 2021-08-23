package com.oner365.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 监控服务
 * @author zhaoyong
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({ "com.oner365.monitor.mapper" })
@ComponentScan({ "com.oner365.common", "com.oner365.monitor", "com.oner365.api" })
public class SpringMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMonitorApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
