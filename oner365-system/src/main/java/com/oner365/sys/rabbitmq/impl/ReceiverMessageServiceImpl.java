package com.oner365.sys.rabbitmq.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.sys.entity.SysMessage;
import com.oner365.sys.rabbitmq.IReceiverMessageService;
import com.oner365.sys.service.ISysMessageService;

/**
 * 消息接收实现类
 * @author zhaoyong
 */
@Service
public class ReceiverMessageServiceImpl implements IReceiverMessageService {
    
    @Autowired
    private ISysMessageService sysMessageService;
    
    @Override
    public void receiver(String message) {
        SysMessage sysMessage = JSON.parseObject(message, SysMessage.class);
        sysMessageService.save(sysMessage);
    }
    
}
