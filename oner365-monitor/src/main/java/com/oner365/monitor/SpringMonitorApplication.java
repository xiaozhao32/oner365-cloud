package com.oner365.monitor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 监控服务
 * @author zhaoyong
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@MapperScan({ "com.oner365.monitor.mapper" })
@ComponentScan({ "com.oner365.common", "com.oner365.monitor", "com.oner365.api" })
public class SpringMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMonitorApplication.class, args);
    }

}
