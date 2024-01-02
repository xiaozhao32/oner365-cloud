package com.oner365.websocket.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.redis.RedisCache;
import com.oner365.websocket.constants.WebSocketConstants;
import com.oner365.websocket.entity.WebSocketData;
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

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

  @Resource
  private IRedisSendMessageService redisSendMessageService;

  @Resource
  private RedisCache redisCache;

  /**
   * webSocket连接创建后调用
   *
   * @throws Exception 异常
   */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    // 获取参数
    String user = String.valueOf(session.getAttributes().get(WebSocketInterceptor.USER));
    String token = String.valueOf(session.getAttributes().get(WebSocketInterceptor.TOKEN));
    String userId = String.valueOf(session.getAttributes().get(WebSocketInterceptor.USER_ID));
    defaultLink(token, user,userId, session);
  }

  /**
   * 接收到消息会调用
   *
   */
  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    if (message instanceof TextMessage) {

      ClientSendVo clientSendVo = JSON.toJavaObject(JSON.parseObject(message.getPayload().toString()),
          ClientSendVo.class);

      switch (clientSendVo.getMessageType()) {
      case DEFAULT:
        redisSendMessageService.sendMessage(new WebSocketMessageVo(session.getAttributes().get(WebSocketInterceptor.USER).toString(),session.getAttributes().get(WebSocketInterceptor.TOKEN).toString(), clientSendVo.getMessage()));
        break;
      case INTERFACE:
        WebSocketData data = WebSocketConstants.userMap.get(session.getAttributes().get(WebSocketInterceptor.TOKEN).toString());
        List<String> tokenList = Lists.newArrayList();
        WebSocketConstants.userMap.values().stream().forEach(d -> tokenList.add(d.getToken()));
        redisSendMessageService.sendMessage(new WebSocketMessageVo(data.getUser(),tokenList, clientSendVo.getMessage()));
        break;
      case HEARTBEAT:
        LOGGER.error("message:{}", JSON.toJSON(clientSendVo));
        redisCache.setCacheObject(WebSocketConstants.HEART_BEAT_KEY + session.getAttributes().get(WebSocketInterceptor.USER_ID).toString(),
            new ClientSendVo(session.getAttributes().get(WebSocketInterceptor.USER).toString(),
                session.getAttributes().get(WebSocketInterceptor.TOKEN).toString(),session.getAttributes().get(WebSocketInterceptor.USER_ID).toString(), session.getId()),
            WebSocketConstants.HEART_BEAT_TIME, TimeUnit.SECONDS);
        break;
      default:
        break;
      }

    } else if (message instanceof BinaryMessage) {

    } else if (message instanceof PongMessage) {

    } else {
      LOGGER.info("Unexpected WebSocket message type: {}", message);
    }
  }

  /**
   * 连接出错会调用
   */
  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    WebSocketConstants.userMap.values().stream().forEach(value -> {
      if(value.getSession().getId().equals(session.getId())) {
        WebSocketConstants.userMap.remove(value.getToken());
      }
    });
    WebSocketConstants.showMessageMap.get(WebSocketConstants.PUBLIC_TOKEN).stream().forEach(data ->{
      if(data.getSession().getId().equals(session.getId())) {
        WebSocketConstants.showMessageMap.get(WebSocketConstants.PUBLIC_TOKEN).remove(data);
      }
    });
  }

  /**
   * 连接关闭会调用
   */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    WebSocketConstants.userMap.values().stream().forEach(value -> {
      if(value.getSession().getId().equals(session.getId())) {
        WebSocketConstants.userMap.remove(value.getToken());
      }
    });
    WebSocketConstants.showMessageMap.get(WebSocketConstants.PUBLIC_TOKEN).stream().forEach(data ->{
      if(data.getSession().getId().equals(session.getId())) {
        WebSocketConstants.showMessageMap.get(WebSocketConstants.PUBLIC_TOKEN).remove(data);
      }
    });
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

  /**
   * 默认链接
   *
   */
  public void defaultLink(String token, String user,String userId, WebSocketSession session) {
    WebSocketData data = WebSocketConstants.userMap.get(token);
    if (DataUtils.isEmpty(data)) {
      data = new WebSocketData(user,token,userId,session);
    }
    WebSocketConstants.userMap.put(token, data);
    List<String> userList = Lists.newArrayList();
    WebSocketConstants.userMap.values().stream().forEach(d -> userList.add(d.getUserId()));
    String jionMessage = "jion&&" + token;
    redisSendMessageService.sendMessage(new WebSocketMessageVo(data.getUser(),WebSocketConstants.PUBLIC_TOKEN, jionMessage));
    redisCache.setCacheObject(WebSocketConstants.HEART_BEAT_KEY +userId, new ClientSendVo(user, token,userId, session.getId()),
        WebSocketConstants.HEART_BEAT_TIME, TimeUnit.SECONDS);
  }


}
