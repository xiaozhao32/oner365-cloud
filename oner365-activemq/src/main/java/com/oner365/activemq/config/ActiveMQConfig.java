package com.oner365.activemq.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oner365.activemq.constants.ActiveMQConstants;

/**
 * ActiveMQ Configuration
 * 
 * @author zhaoyong
 */
@Configuration
public class ActiveMQConfig {
  
  @Bean
  Queue queue() {
    return new ActiveMQQueue(ActiveMQConstants.QUEUE_NAME);
  }
  
}
