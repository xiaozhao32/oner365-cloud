package com.oner365.websocket.dto;

import java.io.Serializable;
import java.util.List;

/**
 * websocket 连接传输类
 * 
 * @author liutao
 */
public class SendMessageDto implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 发送失败列表
   */
  private List<String> failList;
  
  /**
   * 发送成功列表
   */
  private List<String> successList;



  public SendMessageDto() {
    super();
  }

  public SendMessageDto(List<String> failList,List<String> successList) {
    this.failList = failList;
    this.successList = successList;
  }

  public List<String> getFailList() {
    return failList;
  }



  public void setFailList(List<String> failList) {
    this.failList = failList;
  }



  public List<String> getSuccessList() {
    return successList;
  }



  public void setSuccessList(List<String> successList) {
    this.successList = successList;
  }

  

}
