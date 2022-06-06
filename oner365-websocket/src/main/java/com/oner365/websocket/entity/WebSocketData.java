package com.oner365.websocket.entity;

import java.io.Serializable;

import org.springframework.web.socket.WebSocketSession;

/**
 * websocket 连接类
 * 
 * @author liutao
 */
public class WebSocketData implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 通道号
   */
  private String token;
  
  /**
   * 通道session
   */
  private WebSocketSession session;
  
  /**
   * 用户名
   */
  private String user;
  
  /**
   * 用户id
   */
  private String userId;

  


  public WebSocketData() {
    super();
  }
  
  public WebSocketData(String user,String token,String userId,WebSocketSession session) {
    this.user = user;
    this.token = token;
    this.session = session;
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public WebSocketSession getSession() {
    return session;
  }

  public void setSession(WebSocketSession session) {
    this.session = session;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }



}
