package com.oner365.websocket.constants;

import com.oner365.common.constants.PublicConstants;

/**
 * websocket消息队列常量
 * @author liutao
 */
public class WebSocketConstants extends PublicConstants {
  

    /**
     * websocket 消息队列标识
     */
    public static final String WEBSOCKET_MESSAGE_QUEUE_NAME = PublicConstants.NAME + "." + "webSocketMessage";

   

    /**
     * Constructor
     */
    private WebSocketConstants() {
        super();
    }
}
