package com.oner365.sys.enums;

import com.oner365.common.enums.BaseEnum;

/**
 * 枚举 - 消息类型
 *
 * @author zhaoyong
 */
public enum MessageTypeEnum implements BaseEnum {

  /** 默认 */
  DEFAULT("默认"),
  /** 测试 */
  TEST("测试");

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
  public String getName() {
    return name;
  }

}
