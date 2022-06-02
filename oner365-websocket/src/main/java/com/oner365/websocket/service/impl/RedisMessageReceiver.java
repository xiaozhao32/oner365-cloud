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
import com.oner365.websocket.enums.MessageTypeEnum;
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

  @Override
  public void onMessage(Message message, byte[] pattern) {
    LOGGER.info("message:{}", new String(message.getBody()));
    WebSocketMessageVo webSocketMessageVo = JSON.toJavaObject(JSON.parseObject(new String(message.getBody())),
        WebSocketMessageVo.class);
    sendMessage(webSocketMessageVo.getToken(), webSocketMessageVo.getUser(), webSocketMessageVo.getMessage(),
        webSocketMessageVo.getMessageType());
  }

  /**
   * 后端发送消息
   * 
   * @throws IOException
   */
  public void sendMessage(String token, String userName, String message, MessageTypeEnum messageType) {
    Map<String, String> users = WebSocketHandler.showMessageMap.get(token);;
    switch (messageType) {
    case DEFAULT:
      users =  WebSocketHandler.showMessageMap.get(token);
      break;
    case HEARTBEAT:
      users =  WebSocketHandler.showMessageMap.get(token);
      break;
    default:
      break;
    }
    if (users != null) {
      LOGGER.info("发送人:{},发送信息:{}", userName, message);
      users.keySet().stream().forEach(key -> {
        WebSocketSession session = null;
        switch (messageType) {
        case DEFAULT:
          session =  WebSocketHandler.sessionMap.get(WebSocketHandler.showMessageMap.get(token).get(key));
          break;
        case HEARTBEAT:
          session =  WebSocketHandler.sessionMap.get(WebSocketHandler.userMap.get(token).get(key));
          break;
        default:
          break;
        }
        try {
          session.sendMessage(new TextMessage(userName + "&&" + message));
        } catch (Exception e) {
          LOGGER.error("sendMessage error:", e);
        }
      });
    }
  }
}
