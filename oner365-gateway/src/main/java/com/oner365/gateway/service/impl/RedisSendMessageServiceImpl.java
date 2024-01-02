package com.oner365.gateway.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.service.IRedisSendMessageService;

/**
 * 发送消息广播实现类
 * @author liutao
 *
 */
@Service
public class RedisSendMessageServiceImpl implements IRedisSendMessageService {
  
  private final Logger logger = LoggerFactory.getLogger(RedisSendMessageServiceImpl.class);

  @Resource
  public RedisTemplate<String,Serializable> redisTemplate;

  @Override
  public void sendRefreshRoute() {
    try {
      redisTemplate.convertAndSend(GatewayConstants.QUEUE_NAME, GatewayConstants.QUEUE_NAME);
    }catch(Exception e) {
      logger.error("sendRefreshRoute error", e);
    }
  }


}
