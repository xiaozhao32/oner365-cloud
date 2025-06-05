package com.oner365.websocket.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.websocket.service.IWebSocketMessageService;
import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * 监听消息广播
 *
 * @author liutao
 *
 */
@Service
public class RedisMessageReceiver implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageReceiver.class);

    @Resource
    private IWebSocketMessageService webSocketMessageService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        byte[] bytes = message.getBody();
        if (bytes.length != 0) {
            String context = new String(bytes);
            LOGGER.info("message:{}", context);
            WebSocketMessageVo webSocketMessageVo = JSON.toJavaObject(JSON.parseObject(context),
                    WebSocketMessageVo.class);
            webSocketMessageService.sendMessage(webSocketMessageVo);
        }
    }

}
