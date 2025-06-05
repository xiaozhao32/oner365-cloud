package com.oner365.sys.rabbitmq.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.redis.RedisCache;
import com.oner365.sys.constants.SysMessageConstants;
import com.oner365.sys.rabbitmq.ISendService;

import javax.annotation.Resource;

/**
 * 消息发送服务实现类
 *
 * @author zhaoyong
 */
@Service
public class SendServiceImpl implements ISendService {

    @Resource
    private RedisCache redisCache;

    @Resource
    private AmqpTemplate rabbitTemplate;

    /**
     * 对应消息监听 @Exchange（ value = "" key = "")
     */
    @Override
    public void send(String message) {
        if (redisCache.lock(SysMessageConstants.QUEUE_NAME, PublicConstants.QUEUE_LOCK_TIME_SECOND)) {
            rabbitTemplate.convertAndSend(SysMessageConstants.QUEUE_TYPE, SysMessageConstants.QUEUE_KEY, message);
        }
    }

}
