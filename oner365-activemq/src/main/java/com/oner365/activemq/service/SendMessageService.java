package com.oner365.activemq.service;

/**
 * Send message service
 *
 * @author zhaoyong
 */
public interface SendMessageService {

    /**
     * 发送消息
     * @param message 发送内容
     */
    void sendMessage(String message);

}
