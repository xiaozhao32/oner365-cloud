package com.oner365.gateway.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
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


  @Autowired
  public RedisTemplate<String,Serializable> redisTemplate;

  @Override
  public void sendRefreshRoute() {
    try {
      redisTemplate.convertAndSend(GatewayConstants.QUEUE_NAME, null);
    }catch(Exception e) {
      e.printStackTrace();
    }
  }


}
