package com.oner365.swagger.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 用户类型状态
 *
 * @author zhaoyong
 */
public enum SysUserTypeEnum implements Serializable {

  /** 默认 */
  NONE("0", "NONE"),
  /** 默认 */
  DEFAULT("1", "默认");

  /**
   * 编码
   */
  private final String code;

  /**
   * 名称
   */
  private final String name;

  /**
   * 构造方法
   *
   * @param code 编码
   * @param name 名称
   */
  SysUserTypeEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  /**
   * get code
   *
   * @return code
   */
  public String getCode() {
    return code;
  }

  /**
   * get name
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * 获取枚举
   *
   * @param code 编码
   * @return MessageStatusEnum
   */
  public static SysUserTypeEnum getCode(String code) {
    Optional<SysUserTypeEnum> result = Arrays.stream(SysUserTypeEnum.values()).filter(e -> e.getCode().equals(code))
        .findFirst();
    return result.orElse(null);
  }

}
