package com.oner365.websocket.service;

import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * 发送消息广播
 * @author liutao
 *
 */
public interface IRedisSendMessageService {

    /**
     * 发送消息到websocket
     *
     * @param WebSocketMessageVo 对象
     */
    void sendMessage(WebSocketMessageVo webSocketMessageVo);
}
