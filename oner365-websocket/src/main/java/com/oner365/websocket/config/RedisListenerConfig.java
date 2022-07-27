package com.oner365.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.oner365.websocket.constants.WebSocketConstants;

/**
 * 消息广播监听配置
 *
 * @author liutao
 */
@Configuration
public class RedisListenerConfig {

  @Autowired
  private MessageListener redisMessageReceiver;

  /**
   * 初始化监听器
   *
   * @param connectionFactory connectionFactory
   * @return RedisMessageListenerContainer
   */
  @Bean
  RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.addMessageListener(new MessageListenerAdapter(redisMessageReceiver),
            new PatternTopic(WebSocketConstants.WEBSOCKET_MESSAGE_QUEUE_NAME));
    return container;
  }

}
