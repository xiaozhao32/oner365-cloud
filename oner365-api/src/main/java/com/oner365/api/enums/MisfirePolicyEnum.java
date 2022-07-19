package com.oner365.api.enums;

import java.util.Arrays;
import java.util.Optional;

import com.oner365.common.enums.BaseEnum;

/**
 * 枚举 - 计划策略类型
 *
 * @author zhaoyong
 */
public enum MisfirePolicyEnum implements BaseEnum {

  /** 默认 */
  DEFAULT("0", "默认"),

  /** 立即触发执行 */
  IGNORE("1", "立即触发执行"),

  /** 触发一次执行 */
  ONCE("2", "触发一次执行"),

  /** 不触发立即执行 */
  NONE("3", "不触发立即执行");

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
  MisfirePolicyEnum(String code, String name) {
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
  @Override
  public String getName() {
    return name;
  }

  /**
   * 获取枚举
   *
   * @param code 编码
   * @return StatusEnum
   */
  public static MisfirePolicyEnum getCode(String code) {
    Optional<MisfirePolicyEnum> result = Arrays.stream(MisfirePolicyEnum.values()).filter(e -> e.getCode().equals(code))
        .findFirst();
    return result.orElse(null);
  }
}
