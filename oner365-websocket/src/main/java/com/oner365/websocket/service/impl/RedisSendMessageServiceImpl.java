package com.oner365.websocket.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RedisSendMessageServiceImpl implements IRedisSendMessageService {
  
  private final Logger logger = LoggerFactory.getLogger(IRedisSendMessageService.class);

  @Resource
  public RedisTemplate<String,Serializable> redisTemplate;

  @Override
  public void sendMessage(WebSocketMessageVo webSocketMessageVo) {
    try {
      redisTemplate.convertAndSend(WebSocketConstants.WEBSOCKET_MESSAGE_QUEUE_NAME, webSocketMessageVo);
    }catch(Exception e) {
      logger.error("sendMessage error", e);
    }
  }


}
