package com.oner365.websocket.enums;

import com.oner365.data.commons.enums.BaseEnum;

/**
 * 枚举 - 通道类型
 *
 * @author LT
 */
public enum MessageTypeEnum implements BaseEnum {

  /** 默认 全员通道 */
  DEFAULT("默认"),
  /** 接口 */
  INTERFACE("接口"),
  /** 服务器与客户端心跳 */
  HEARTBEAT("心跳");

  /**
   * 名称
   */
  private final String name;

  /**
   * 构造方法
   *
   * @param name 名称
   */
  MessageTypeEnum(String name) {
    this.name = name;
  }

  /**
   * get name
   *
   * @return name
   */
  @Override
  public String getName() {
    return name;
  }

}
