package com.oner365.websocket.vo;

import java.io.Serializable;

/**
 * websocket 连接传输类
 *
 * @author liutao
 */
public class WebSocketLinkVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String user;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 通道标识
     */
    private String token;

    public WebSocketLinkVo() {
        super();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
