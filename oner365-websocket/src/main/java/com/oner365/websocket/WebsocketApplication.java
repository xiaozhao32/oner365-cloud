package com.oner365.websocket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan({ "com.oner365.websocket.mapper" })
@ComponentScan({ "com.oner365.common","com.oner365.websocket" })
public class WebsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

	

}
