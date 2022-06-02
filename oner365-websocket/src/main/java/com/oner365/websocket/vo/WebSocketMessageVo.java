package com.oner365.websocket.vo;

import java.io.Serializable;

/**
 * websocket 消息传输类
 * 
 * @author liutao
 */
public class WebSocketMessageVo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 用户名
   */
  private String user;

  /**
   * 通道标识
   */
  private String token;
  
  /**
   * 消息
   */
  private String message;


  public WebSocketMessageVo() {
    super();
  }
  
  public WebSocketMessageVo(String user,String token,String message) {
    this.message = message;
    this.token = token;
    this.user = user;
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


  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }


}
