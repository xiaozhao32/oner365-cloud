package com.oner365.websocket.vo;

import java.io.Serializable;

import com.oner365.websocket.enums.MessageTypeEnum;

/**
 * websocket 连接传输类
 * 
 * @author liutao
 */
public class ClientSendVo implements Serializable {

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
  private String token;
  
  /**
   * session id
   */
  private String sessionId;
  
  /**
   * 用户名
   */
  private String user;
  
  /**
   * 用户名
   */
  private String userId;

  
  /**
   * 通道类型
   */
  private MessageTypeEnum messageType;


  public ClientSendVo() {
    super();
  }
  
  public ClientSendVo(String user,String token,String userId ,String sessionId) {
    this.user = user;
    this.token = token;
    this.sessionId = sessionId;
    this.userId = userId;
  }


  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }


  public MessageTypeEnum getMessageType() {
    return messageType;
  }

  public void setMessageType(MessageTypeEnum messageType) {
    this.messageType = messageType;
  }

  public String getToken() {
    return token;
  }


  public void setToken(String token) {
    this.token = token;
  }


  public String getUser() {
    return user;
  }


  public void setUser(String user) {
    this.user = user;
  }


  public String getSessionId() {
    return sessionId;
  }


  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


}
