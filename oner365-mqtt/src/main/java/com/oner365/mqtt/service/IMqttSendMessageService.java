package com.oner365.mqtt.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.oner365.mqtt.constants.MqttConstants;
import com.oner365.mqtt.constants.QueueConstants;

/**
 * Mqtt message service
 * 
 * @author zhaoyong
 */
@Service
@MessagingGateway(defaultRequestChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME)
public interface IMqttSendMessageService {

  /**
   * 发送消息 默认topic
   *
   * @param message 发送内容
   */
  void sendMessage(String message);

  /**
   * 发送消息 指定topic
   *
   * @param topic   topic
   * @param message 发送内容
   */
  void sendMessage(@Header(MqttHeaders.TOPIC) String topic, String message);

  /**
   * 发送消息 指定topic 指定qos
   *
   * @param topic   topic
   * @param qos     qos
   * @param message 发送内容
   */
  void sendMessage(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String message);
}
