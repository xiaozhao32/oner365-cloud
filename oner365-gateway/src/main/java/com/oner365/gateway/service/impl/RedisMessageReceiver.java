package com.oner365.gateway.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.oner365.gateway.service.DynamicRouteService;

/**
 * 监听消息广播
 * 
 * @author liutao
 *
 */
@Service
public class RedisMessageReceiver implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageReceiver.class);
  
  @Autowired
  private DynamicRouteService dynamicRouteService;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    LOGGER.info("RefreshRoute:ok");
    dynamicRouteService.findList();
   
  }
}
