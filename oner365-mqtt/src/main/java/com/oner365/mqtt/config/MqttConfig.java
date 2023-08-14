package com.oner365.mqtt.config;

import javax.annotation.Resource;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.oner365.mqtt.config.properties.MqttProperties;
import com.oner365.mqtt.constants.MqttConstants;
import com.oner365.mqtt.constants.QueueConstants;

/**
 * MQTT config
 * 
 * @author zhaoyong
 *
 */
@Configuration
@EnableConfigurationProperties({ MqttProperties.class })
public class MqttConfig {

  private final Logger logger = LoggerFactory.getLogger(MqttConfig.class);

  @Resource
  private MqttProperties mqttProperties;

  public MqttConfig() {
    logger.info("Queue Type: {}", "MQTT");
  }

  @Bean
  MqttPahoClientFactory mqttPahoClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    options.setCleanSession(false);
    options.setServerURIs(mqttProperties.getUri().split(","));
    options.setUserName(mqttProperties.getUsername());
    options.setPassword(mqttProperties.getPassword().toCharArray());
    factory.setConnectionOptions(options);
    return factory;
  }

  @Bean
  MqttPahoMessageDrivenChannelAdapter adapter() {
    // clientId不能重复
    return new MqttPahoMessageDrivenChannelAdapter(mqttProperties.getClientId() + MqttConstants.CHANNEL_ADAPTER,
        mqttPahoClientFactory(), QueueConstants.MESSAGE_QUEUE_NAME);
  }

  @Bean
  MessageProducer messageInbound(MqttPahoMessageDrivenChannelAdapter adapter) {
    // 入站投递的通道
    adapter.setOutputChannel(messageInboundChannel());
    adapter.setCompletionTimeout(MqttConstants.COMPLETION_TIMEOUT);
    adapter.setQos(MqttConstants.QOS);
    return adapter;
  }

  @Bean
  @ServiceActivator(inputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
  MessageHandler messageOutbound() {
    MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
        mqttProperties.getClientId() + MqttConstants.CHANNEL_PRODUCER, mqttPahoClientFactory());
    handler.setAsync(true);
    handler.setDefaultTopic(QueueConstants.MESSAGE_QUEUE_NAME);
    return handler;
  }

  @Bean(name = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
  MessageChannel messageOutboundChannel() {
    return new DirectChannel();
  }

  @Bean(name = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
  MessageChannel messageInboundChannel() {
    return new DirectChannel();
  }
  
}
