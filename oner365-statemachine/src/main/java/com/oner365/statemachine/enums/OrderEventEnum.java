package com.oner365.statemachine.enums;

import com.oner365.data.commons.enums.BaseEnum;

/**
 * 枚举 - 订单事件
 *
 * @author zhaoyong
 */
public enum OrderEventEnum implements BaseEnum {
  
  /** 已支付 */
  PAY("已支付"),
  /** 已收货 */
  RECEIVE("已收货");

  /**
   * 名称
   */
  private final String name;

  /**
   * 构造方法
   *
   * @param name 名称
   */
  OrderEventEnum(String name) {
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
