package com.oner365.sys.rabbitmq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.sys.rabbitmq.IReceiverMessageService;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.sys.vo.SysMessageVo;

/**
 * 消息接收实现类
 * 
 * @author zhaoyong
 */
@Service
public class ReceiverMessageServiceImpl implements IReceiverMessageService {

  @Autowired
  private ISysMessageService sysMessageService;

  @Override
  public void receiver(String message) {
    SysMessageVo sysMessage = JSON.parseObject(message, SysMessageVo.class);
    sysMessageService.save(sysMessage);
  }

}
