package com.oner365.websocket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.oner365.websocket.constants.WebSocketConstants;
import com.oner365.websocket.service.IRedisSendMessageService;
import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * 发送消息广播实现类
 * @author liutao
 *
 */
@Service
@SuppressWarnings(value = { "rawtypes" })
public class RedisSendMessageServiceImpl implements IRedisSendMessageService {


  @Autowired
  public RedisTemplate redisTemplate;

  @Override
  public void sendMessage(WebSocketMessageVo webSocketMessageVo) {
    try {
      redisTemplate.convertAndSend(WebSocketConstants.WEBSOCKET_MESSAGE_QUEUE_NAME, webSocketMessageVo);
    }catch(Exception e) {
      e.printStackTrace();
    }
  }


}
