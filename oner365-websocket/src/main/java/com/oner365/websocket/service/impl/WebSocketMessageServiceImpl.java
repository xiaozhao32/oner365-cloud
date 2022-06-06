package com.oner365.websocket.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.oner365.util.DataUtils;
import com.oner365.websocket.config.WebSocketHandler;
import com.oner365.websocket.constants.WebSocketConstants;
import com.oner365.websocket.service.IWebSocketMessageService;
import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * 消息发送
 * 
 * @author liutao
 *
 */
@Service
public class WebSocketMessageServiceImpl implements IWebSocketMessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);

  @Override
  public void sendMessage(WebSocketMessageVo webSocketMessageVo) {
    if (DataUtils.isEmpty(webSocketMessageVo.getToken())) {
      webSocketMessageVo.getList().stream().forEach(userId -> {
        WebSocketConstants.userMap.values().stream().forEach(data -> {
          if (data.getUserId().equals(userId)) {
            try {
              data.getSession()
                  .sendMessage(new TextMessage(webSocketMessageVo.getUser() + "&&" + webSocketMessageVo.getMessage()));
            } catch (IOException e) {
              LOGGER.error("sendMessage error:", e);
            }
          }
        });

      });
    } else {
      // TODO 暂时是有个默认的10000组，是全员的聊天，以后组信息可以存入数据库，根据组成员发送消息，暂时就是接到有token信息的请求就发全员
      WebSocketConstants.userMap.values().stream().forEach(data -> {
        try {
          data.getSession()
              .sendMessage(new TextMessage(webSocketMessageVo.getUser() + "&&" + webSocketMessageVo.getMessage()));
        } catch (IOException e) {
          LOGGER.error("sendMessage error:", e);
        }
      });
    }
  }
}
