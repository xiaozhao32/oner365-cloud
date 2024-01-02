package com.oner365.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * websocket 启动类
 * @author liutao
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan({ "com.oner365" })
public class WebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

	

}
