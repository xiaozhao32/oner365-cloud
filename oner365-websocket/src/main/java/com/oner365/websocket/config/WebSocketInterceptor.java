package com.oner365.websocket.config;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.oner365.common.sequence.sequence.SnowflakeSequence;
import com.oner365.util.DataUtils;
import com.oner365.websocket.vo.WebSocketLinkVo;

/**
 * websocket连接拦截类
 *
 * @author liutao
 */
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketInterceptor.class);

  public static final String TOKEN = "token";

  public static final String USER = "user";
  
  public static final String USER_ID = "userId";
  
  @Autowired
  private SnowflakeSequence snowflakeSequence;
  
  

  /**
   * handler处理前调用,attributes属性最终在WebSocketSession里,可通过webSocketSession.getAttributes().get(key值)获得
   */
  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Map<String, Object> attributes) {
    LOGGER.info("握手开始");
    // 获得请求参数
    WebSocketLinkVo websocketLinkVo = DataUtils.getUrlParam(request.getURI().getQuery(), WebSocketLinkVo.class);
    if (!DataUtils.isEmpty(websocketLinkVo.getUser())) {
      // 放入属性域
      attributes.put(TOKEN, websocketLinkVo.getToken() != null ? websocketLinkVo.getToken():snowflakeSequence.nextNo());
      attributes.put(USER, websocketLinkVo.getUser());
      attributes.put(USER_ID, websocketLinkVo.getUserId());
      LOGGER.info("用户:{} 握手成功！通道:{},userId:{} ", websocketLinkVo.getUser(), websocketLinkVo.getToken(),websocketLinkVo.getUserId());
      return true;
    }
    LOGGER.info("用户登录已失效");
    return false;
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Exception exception) {

  }
}
