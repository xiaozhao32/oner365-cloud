package com.oner365.websocket.config;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.oner365.common.cache.RedisCache;
import com.oner365.websocket.enums.MessageTypeEnum;
import com.oner365.websocket.service.IRedisSendMessageService;
import com.oner365.websocket.vo.ClientSendVo;
import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * websocket控制器
 *
 * @author liutao
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
  public static Map<String, Map<String, String>> showMessageMap = new ConcurrentHashMap<>();

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

  public static final String PUBLIC_TOKEN = "10000";

  public static final String HEART_BEAT_KEY = "heartBeat::";

  public static int HEART_BEAT_TIME = 120;

  @Autowired
  private IRedisSendMessageService redisSendMessageService;

  @Autowired
  private RedisCache redisCache;

//  @Autowired
//  private RedisCache redisCache;

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
    defaultLink(token, user, session);
  }

  /**
   * 接收到消息会调用
   */
  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    if (message instanceof TextMessage) {
      
      ClientSendVo clientSendVo = JSON.toJavaObject(JSON.parseObject(message.getPayload().toString()),
          ClientSendVo.class);
      switch (clientSendVo.getMessageType()) {
      case DEFAULT:
        sendMessage(PUBLIC_TOKEN, session.getAttributes().get(WebSocketInterceptor.USER).toString(),
            clientSendVo.getMessage(), clientSendVo.getMessageType());
        break;
      case HEARTBEAT:
        LOGGER.error("message:{}", JSON.toJSON(clientSendVo));
        redisCache.setCacheObject(HEART_BEAT_KEY + session.getId(),
            new ClientSendVo(session.getAttributes().get(WebSocketInterceptor.USER).toString(),
                session.getAttributes().get(WebSocketInterceptor.TOKEN).toString(), session.getId()),
            HEART_BEAT_TIME, TimeUnit.SECONDS);
        break;
      default:
        break;
      }

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
   * 默认链接
   * 
   */
  public void defaultLink(String token, String user, WebSocketSession session) {
    Map<String, String> users = userMap.get(token);
    if (users == null) {
      users = Maps.newConcurrentMap();
    }
    users.put(user, session.getId());
    showMessageMap.put(PUBLIC_TOKEN, users);
    userMap.put(token, users);
    sessionMap.put(session.getId(), session);
    String jionMessage = "jion&&" + token;
    sendMessage(PUBLIC_TOKEN, user, jionMessage, MessageTypeEnum.DEFAULT);
    redisCache.setCacheObject(HEART_BEAT_KEY + session.getId(), new ClientSendVo(user, token, session.getId()),
        HEART_BEAT_TIME, TimeUnit.SECONDS);
  }

  /**
   * 后端发送消息
   * 
   * @throws IOException
   */
  public void sendMessage(String token, String user, String message, MessageTypeEnum messageType) {
    Map<String, String> users = null;
    switch (messageType) {
    case DEFAULT:
      users = showMessageMap.get(token);
      break;
    case HEARTBEAT:
      users = userMap.get(token);
      break;
    default:
      break;
    }
    if (users != null) {
      redisSendMessageService.sendMessage(new WebSocketMessageVo(token,user, message,messageType));
    }
  }
}
