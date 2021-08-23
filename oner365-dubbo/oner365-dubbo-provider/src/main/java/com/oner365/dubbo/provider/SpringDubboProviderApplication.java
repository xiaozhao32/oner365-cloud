package com.oner365.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Dubbo 提供者服务
 * @author zhaoyong
 */
@EnableDubbo
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.oner365.dubbo" })
public class SpringDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDubboProviderApplication.class, args);
    }

}
