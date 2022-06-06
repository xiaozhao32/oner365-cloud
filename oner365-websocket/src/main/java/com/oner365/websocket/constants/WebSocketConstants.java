package com.oner365.websocket.constants;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.oner365.common.constants.PublicConstants;
import com.oner365.websocket.entity.WebSocketData;

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
     * websocket 消息队列类型
     */
    public static final String WEBSOCKET_MESSAGE_QUEUE_TYPE= WEBSOCKET_MESSAGE_QUEUE_NAME + "." + PublicConstants.MQ_FANOUT;

    /**
     * websocket 消息队列标识
     */
    public static final String WEBSOCKETE_MESSAGW_QUEUE_KEY = WEBSOCKET_MESSAGE_QUEUE_NAME + "." + PublicConstants.MQ_KEY;

    /**
     * 存储sessionId和webSocketSession
     * 需要注意的是，webSocketSession没有提供无参构造，不能进行序列化，也就不能通过redis存储
     * 在分布式系统中，要想别的办法实现webSocketSession共享
     */
    
    
    /**
     * websocket 用户存储
     */
    public static Map<String, WebSocketData> userMap = new ConcurrentHashMap<>();
   
    /**
     * websocket 显示消息通道存储
     */
    public static Map<String, List<WebSocketData>> showMessageMap = new ConcurrentHashMap<>();


    /**
     * websocket 显示消息通道token
     */
    public static final String PUBLIC_TOKEN = "10000";

    /**
     * websocket 心跳key
     */
    public static final String HEART_BEAT_KEY = "heartBeat::";

    /**
     * websocket 心跳在线时间
     */
    public static int HEART_BEAT_TIME = 120;

    /**
     * Constructor
     */
    private WebSocketConstants() {
        super();
    }
}
