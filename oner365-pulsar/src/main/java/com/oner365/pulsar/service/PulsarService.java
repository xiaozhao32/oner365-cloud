package com.oner365.pulsar.service;

import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.enums.ResultEnum;

/**
 * pulsar service
 *
 * @author zhaoyong
 *
 */
public interface PulsarService {

    /**
     * 发送消息
     * @param message 消息
     * @return 发送结果
     */
    ResultEnum convertAndSend(JSONObject message);

}
