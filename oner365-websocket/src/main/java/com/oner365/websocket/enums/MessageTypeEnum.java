package com.oner365.websocket.enums;

import java.io.Serializable;

/**
 * 枚举 - 通道类型
 *
 * @author LT
 */
public enum MessageTypeEnum implements Serializable {

  /** 默认 全员通道*/
  DEFAULT,
  /** 接口*/
  INTERFACE,
  /** 服务器与客户端心跳 */
  HEARTBEAT
  

}
