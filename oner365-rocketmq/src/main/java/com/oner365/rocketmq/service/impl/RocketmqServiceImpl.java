package com.oner365.rocketmq.service.impl;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.rocketmq.config.properties.RocketmqProperties;
import com.oner365.rocketmq.service.RocketmqService;

/**
 * Rocketmq 实现类
 *
 * @author zhaoyong
 *
 */
@Service
public class RocketmqServiceImpl implements RocketmqService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RocketmqServiceImpl.class);

    @Resource
    private RocketmqProperties rocketmqProperties;

    @Resource
    private RocketMQTemplate rocketmqTemplate;

    @Override
    public ResultEnum convertAndSend(Object message) {
        try {
            rocketmqTemplate.convertAndSend(rocketmqProperties.getTopic(), message);
            return ResultEnum.SUCCESS;
        }
        catch (MessagingException e) {
            LOGGER.error("convertAndSend error:", e);
        }
        return ResultEnum.ERROR;
    }

}
