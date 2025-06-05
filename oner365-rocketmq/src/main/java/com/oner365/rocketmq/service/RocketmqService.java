package com.oner365.rocketmq.service;

import com.oner365.data.commons.enums.ResultEnum;

/**
 * Rocketmq Service
 *
 * @author zhaoyong
 *
 */
public interface RocketmqService {

    /**
     * 发送消息
     * @param message 消息
     * @return 发送结果
     */
    ResultEnum convertAndSend(Object message);

}
