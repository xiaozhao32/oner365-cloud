package com.oner365.gateway.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.rabbitmq.ISyncRouteMqService;
import com.oner365.gateway.util.DataUtils;

import javax.annotation.Resource;

/**
 * ISyncRouteMQService实现类
 * @author zhaoyong
 */
@Service
public class SyncRouteMqServiceImpl implements ISyncRouteMqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncRouteMqServiceImpl.class);

    @Resource
    private AmqpTemplate rabbitTemplate;

    @Override
    public void syncRoute() {
        LOGGER.info("MQ push: {}", DataUtils.getLocalhost());
        rabbitTemplate.convertAndSend(GatewayConstants.QUEUE_TYPE, GatewayConstants.QUEUE_KEY, DataUtils.getLocalhost());
    }

}
