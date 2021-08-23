package com.oner365.common.rabbitmq.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ Config
 * @author zhaoyong
 *
 */
@Configuration
public class RabbitmqConfig {
    
    @Bean("customerContainerFactory")
    public SimpleRabbitListenerContainerFactory customerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // 设置线程数
        factory.setConcurrentConsumers(10);
        // 最大线程数
        factory.setMaxConcurrentConsumers(30);
        configurer.configure(factory, connectionFactory);
        return factory;
    }
    
}
