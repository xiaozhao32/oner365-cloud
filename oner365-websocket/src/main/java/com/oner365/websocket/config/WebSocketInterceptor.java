package com.oner365.websocket.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

/**
 * websocket 拦截器
 * @author liutao
 *
 */
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketInterceptor.class);

  public static final String TOKEN = "token";

  public static final String USER = "user";

  /**
   * handler处理前调用,attributes属性最终在WebSocketSession里,可能通过webSocketSession.getAttributes().get(key值)获得
   */
  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
      Map<String, Object> attributes) {
    LOGGER.info("握手开始");
    // 获得请求参数
    HashMap<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), "utf-8");
    String token = paramMap.get(TOKEN);
    String user = paramMap.get(USER);
    if (StrUtil.isNotBlank(user)) {
      // 放入属性域
      attributes.put(TOKEN, token);
      attributes.put(USER, user);
      LOGGER.info("用户:{} 握手成功！通道:{} " ,user,token);
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
