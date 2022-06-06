package com.oner365.websocket.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.websocket.config.WebSocketHandler;
import com.oner365.websocket.service.IWebSocketMessageService;
import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * 监听消息广播
 * 
 * @author liutao
 *
 */
@Service("RedisMessageReceiver")
public class RedisMessageReceiver implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
  
  @Autowired
  private IWebSocketMessageService webSocketMessageService;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    LOGGER.info("message:{}", new String(message.getBody()));
    WebSocketMessageVo webSocketMessageVo = JSON.toJavaObject(JSON.parseObject(new String(message.getBody())),
        WebSocketMessageVo.class);
    webSocketMessageService.sendMessage(webSocketMessageVo);
  }
}
