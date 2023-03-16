package com.oner365.monitor.config;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rabbit admin config
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class RabbitAdminConfig {
  
  @Resource
  private ConnectionFactory connectionFactory;

  @Bean
  RabbitAdmin rabbitAdmin() {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    rabbitAdmin.setAutoStartup(true);
    return rabbitAdmin;
  }

}
