package com.oner365.activemq.service.impl;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.oner365.activemq.service.SendMessageService;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    private final Logger logger = LoggerFactory.getLogger(SendMessageService.class);

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Resource
    private Queue queue;

    @Override
    public void sendMessage(String message) {
        logger.info("ActiveMQ Send Message: {}", message);
        jmsMessagingTemplate.convertAndSend(queue, message);
    }

}
