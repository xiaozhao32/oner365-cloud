package com.oner365.mqtt.service;

import com.oner365.common.service.BaseService;

/**
 * MQTT 接收者
 *
 * @author zhaoyong
 *
 */
public interface IMqttReceiverMessageService extends BaseService {

  /**
   * 接收消息
   *
   * @param message 消息内容
   */
  void message(Object message);
}
