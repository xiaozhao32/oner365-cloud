package com.oner365.websocket.config;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.google.common.collect.Maps;
import com.oner365.websocket.service.IRedisSendMessageService;
import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * websocket 控制器
 * 
 * @author liutao
 *
 */
@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

  /**
   * 存储sessionId和webSocketSession
   * 需要注意的是，webSocketSession没有提供无参构造，不能进行序列化，也就不能通过redis存储
   * 在分布式系统中，要想别的办法实现webSocketSession共享
   */
  public static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
  public static Map<String, Map<String, String>> userMap = new ConcurrentHashMap<>();

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

  @Autowired
  private IRedisSendMessageService redisSendMessageService;

  /**
   * webSocket连接创建后调用
   * 
   * @throws Exception
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // 获取参数
    String user = String.valueOf(session.getAttributes().get(WebSocketInterceptor.USER));
    String token = String.valueOf(session.getAttributes().get(WebSocketInterceptor.TOKEN));
    Map<String, String> users = userMap.get(token);
    if (users == null) {
      users = Maps.newHashMap();
    }
    users.put(user, session.getId());
    userMap.put(token, users);
    sessionMap.put(session.getId(), session);
    String jionMessage = "jion";
    sendMessage(session.getAttributes().get(WebSocketInterceptor.USER).toString(),
            session.getAttributes().get(WebSocketInterceptor.TOKEN).toString(), jionMessage);
  }

  /**
   * 接收到消息会调用
   */
  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    if (message instanceof TextMessage) {
      sendMessage(session.getAttributes().get(WebSocketInterceptor.USER).toString(),
              session.getAttributes().get(WebSocketInterceptor.TOKEN).toString(), message.getPayload().toString());
    } else if (message instanceof BinaryMessage) {

    } else if (message instanceof PongMessage) {

    } else {
      LOGGER.info("Unexpected WebSocket message type: " + message);
    }
  }

  /**
   * 连接出错会调用
   */
  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    sessionMap.remove(session.getId());
  }

  /**
   * 连接关闭会调用
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    sessionMap.remove(session.getId());
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

  /**
   * 后端发送消息
   * 
   * @throws IOException
   */
  public void sendMessage(String token, String user, String message) {
    Map<String, String> users = userMap.get(token);
    if (users != null) {
      redisSendMessageService.sendMessage(new WebSocketMessageVo(user,token, message));
    }
  }
}
