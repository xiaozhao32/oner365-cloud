package com.oner365.websocket.vo;

import java.io.Serializable;
import java.util.List;

/**
 * websocket 连接传输类
 *
 * @author liutao
 */
public class ClientSendMessageVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 消息
     */
    private String message;

    /**
     * 通道号
     */
    private List<String> userIds;

    public ClientSendMessageVo() {
        super();
    }

    public ClientSendMessageVo(List<String> userIds, String message) {
        this.userIds = userIds;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

}
