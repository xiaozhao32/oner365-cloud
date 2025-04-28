package com.oner365.activemq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.oner365.activemq.constants.ActiveMQConstants;

/**
 * ActiveMQ Listener
 * 
 * @author zhaoyong
 */
@Component
public class ReceiveMessageListener {

  private final Logger logger = LoggerFactory.getLogger(ReceiveMessageListener.class);

  @JmsListener(destination = ActiveMQConstants.QUEUE_NAME)
  public void receiveMessage(String text) {
    logger.info("ActiveMQ Receive message: {}", text);
  }

}
