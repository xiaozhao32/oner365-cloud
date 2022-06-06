package com.oner365.websocket.vo;

import java.io.Serializable;
import java.util.List;

import com.oner365.websocket.entity.WebSocketData;
import com.oner365.websocket.enums.MessageTypeEnum;

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
   * group token
   */
  private String token;
  

  /**
   * 发送人信息
   */
  private WebSocketData data;
  
  /**
   * 接收消息人
   */
  private List<String> list;
  
  /**
   * 消息
   */
  private String message;
  
  /**
   * 通道类型
   */
  private MessageTypeEnum messageType;
  
  
  


  public WebSocketMessageVo() {
    super();
  }
  
  public WebSocketMessageVo(String userName,List<String> list,String message) {
    this.message = message;
    this.user = userName;
    this.list = list;
  }
  
  public WebSocketMessageVo(String userName,String token,String message) {
    this.message = message;
    this.user = userName;
    this.token = token;
  }


  public String getUser() {
    return user;
  }


  public void setUser(String user) {
    this.user = user;
  }




  public WebSocketData getData() {
    return data;
  }

  public void setData(WebSocketData data) {
    this.data = data;
  }

  public List<String> getList() {
    return list;
  }

  public void setList(List<String> list) {
    this.list = list;
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
  
  

}
