package com.oner365.websocket.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket配置注入类
 *
 * @author liutao
 */
@Configuration
@EnableWebSocket
public class WebSocketAutoConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketHandler webSocketHandler;

    @Resource
    private WebSocketInterceptor webSocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // webSocket通道
        // 指定处理器和路径
        registry.addHandler(webSocketHandler, "/websocket")
            // 指定自定义拦截器
            .addInterceptors(webSocketInterceptor)
            // 允许跨域
            .setAllowedOrigins("*");
        // sockJs通道
        registry.addHandler(webSocketHandler, "/sock-js")
            .addInterceptors(webSocketInterceptor)
            .setAllowedOrigins("*")
            // 开启sockJs支持
            .withSockJS();
    }

}
