package com.oner365.elasticsearch.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 基因类型
 *
 * @author zhaoyong
 */
public enum GeneTypeEnum implements Serializable {

  /** X */
  X("0", "X"),
  /** Y */
  Y("1", "Y"),
  /** Z */
  Z("2", "Z");

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
  GeneTypeEnum(String code, String name) {
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
   * @return StatusEnum
   */
  public static GeneTypeEnum getCode(String code) {
    Optional<GeneTypeEnum> result = Arrays.stream(GeneTypeEnum.values()).filter(e -> e.getCode().equals(code))
        .findFirst();
    return result.orElse(null);
  }

}
