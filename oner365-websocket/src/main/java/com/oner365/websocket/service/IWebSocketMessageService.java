package com.oner365.websocket.service;

import com.oner365.websocket.vo.WebSocketMessageVo;

/**
 * 发送消息广播
 *
 * @author liutao
 *
 */
public interface IWebSocketMessageService {

    /**
     * 发送消息到websocket
     * @param webSocketMessageVo 对象
     */
    void sendMessage(WebSocketMessageVo webSocketMessageVo);

}
