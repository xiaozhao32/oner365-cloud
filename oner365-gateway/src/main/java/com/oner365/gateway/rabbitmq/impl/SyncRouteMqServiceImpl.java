package com.oner365.gateway.rabbitmq.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.redis.RedisCache;
import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.rabbitmq.ISyncRouteMqService;
import com.oner365.gateway.util.DataUtils;

/**
 * ISyncRouteMQService实现类
 * @author zhaoyong
 */
@Service
public class SyncRouteMqServiceImpl implements ISyncRouteMqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SyncRouteMqServiceImpl.class);
    
    @Resource
    private RedisCache redisCache;

    @Resource
    private AmqpTemplate rabbitTemplate;

    @Override
    public void syncRoute() {
        if (redisCache.lock(GatewayConstants.QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            LOGGER.info("MQ push: {}", DataUtils.getLocalhost());
            rabbitTemplate.convertAndSend(GatewayConstants.QUEUE_TYPE, GatewayConstants.QUEUE_KEY, DataUtils.getLocalhost());
        }
    }

}
