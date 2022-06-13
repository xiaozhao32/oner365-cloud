package com.oner365.gateway.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.oner365.gateway.constants.GatewayConstants;

/**
 * 消息广播监听配置
 *
 * @author liutao
 */
@Configuration
public class RedisListenerConfig {

  @Resource(name = "RedisMessageReceiver")
  private MessageListener redisMessageReceiver;

  // 初始化监听器
  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(new MessageListenerAdapter(redisMessageReceiver),
        new PatternTopic(GatewayConstants.QUEUE_NAME));
    return container;
  }

}