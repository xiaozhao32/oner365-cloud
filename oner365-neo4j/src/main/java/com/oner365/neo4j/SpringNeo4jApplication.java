package com.oner365.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Neo4j 服务
 *
 * @author zhaoyong
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan({ "com.oner365" })
public class SpringNeo4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNeo4jApplication.class, args);
    }

}
