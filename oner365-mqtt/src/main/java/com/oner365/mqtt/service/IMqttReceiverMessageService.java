package com.oner365.mqtt.service;

/**
 * MQTT 接收者
 *
 * @author zhaoyong
 *
 */
public interface IMqttReceiverMessageService {

  /**
   * 接收消息
   *
   * @param message 消息内容
   */
  void message(Object message);
}
