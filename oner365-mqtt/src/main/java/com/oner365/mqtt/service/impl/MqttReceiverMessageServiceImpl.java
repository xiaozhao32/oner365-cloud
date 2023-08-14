package com.oner365.mqtt.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.oner365.mqtt.constants.MqttConstants;
import com.oner365.mqtt.constants.QueueConstants;
import com.oner365.mqtt.service.IMqttReceiverMessageService;

/**
 * MQTT 接收实现
 * 
 * @author zhaoyong
 *
 */
@Service
public class MqttReceiverMessageServiceImpl implements IMqttReceiverMessageService {

  private final Logger logger = LoggerFactory.getLogger(MqttReceiverMessageServiceImpl.class);

  @Override
  @ServiceActivator(
      inputChannel = MqttConstants.IN_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME, 
      outputChannel = MqttConstants.OUT_BOUND_CHANNEL + QueueConstants.MESSAGE_QUEUE_NAME
  )
  public void message(Object message) {
    logger.info("Mqtt receive message: {}", message);
  }

}
