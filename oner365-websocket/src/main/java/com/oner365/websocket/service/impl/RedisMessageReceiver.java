package com.oner365.websocket.service.impl;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.oner365.websocket.config.WebSocketHandler;
import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * 监听消息广播
 * @author liutao
 *
 */
@Service("RedisMessageReceiver")
public class RedisMessageReceiver implements MessageListener{
  
  
  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
  
  @Override
  public void onMessage(Message message, byte[] pattern) {
    LOGGER.info("message:{}",new String(message.getBody()));
    WebSocketMessageVo webSocketMessageVo = JSON.toJavaObject(JSON.parseObject(new String(message.getBody())), WebSocketMessageVo.class);
    sendMessage(webSocketMessageVo.getToken(), webSocketMessageVo.getUser(), webSocketMessageVo.getMessage());
  }
  
  /**
   * 后端发送消息
   * 
   * @throws IOException
   */
  public void sendMessage(String token, String user, String message) {
    Map<String, String> users = WebSocketHandler.userMap.get(token);
    if (users != null) {
      LOGGER.info("发送人:{},发送信息:{},发送通道:{}", user, message, token);
      users.keySet().stream().forEach(key -> {
        WebSocketSession session = WebSocketHandler.sessionMap.get(users.get(key));
        try {
          session.sendMessage(new TextMessage(user + "&&" + message));
        } catch (Exception e) {
          LOGGER.error("sendMessage error:",e);
        }
      });
    }
  }
}
