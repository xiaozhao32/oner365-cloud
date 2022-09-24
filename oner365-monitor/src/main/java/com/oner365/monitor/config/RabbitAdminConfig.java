package com.oner365.monitor.config;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Rabbit admin config
 * 
 * @author zhaoyong
 *
 */
@Component
public class RabbitAdminConfig {
  
  @Resource
  private ConnectionFactory connectionFactory;

  @Bean
  public RabbitAdmin rabbitAdmin() {
    RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
    rabbitAdmin.setAutoStartup(true);
    return rabbitAdmin;
  }

}
