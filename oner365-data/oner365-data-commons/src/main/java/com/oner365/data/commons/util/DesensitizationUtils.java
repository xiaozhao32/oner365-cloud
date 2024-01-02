package com.oner365.data.commons.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏工具类
 *
 * @author zhaoyong
 *
 */
public class DesensitizationUtils {

  private static final int EIGHT = 8;
  private static final int ELEVEN = 11;

  private DesensitizationUtils() {
  }

  /**
   * 只显示第一个汉字，其他隐藏为2个星号<李**>
   *
   * @param fullName 名称
   * @param index    第index位开始脱敏
   * @return String
   */
  public static String left(String fullName, int index) {
    if (StringUtils.isBlank(fullName)) {
      return null;
    }
    String name = StringUtils.left(fullName, index);
    return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
  }

  /**
   * 110****58，前面保留3位明文，后面保留2位明文
   *
   * @param name  名称
   * @param index 3位
   * @param end   2位
   * @return String
   */
  public static String around(String name, int index, int end) {
    if (StringUtils.isBlank(name)) {
      return null;
    }
    return StringUtils.left(name, index).concat(StringUtils
        .removeStart(StringUtils.leftPad(StringUtils.right(name, end), StringUtils.length(name), "*"), "***"));
  }

  /**
   * 后四位，其他隐藏<****1234>
   *
   * @param num 数字
   * @param end 尾数
   * @return String
   */
  public static String right(String num, int end) {
    if (StringUtils.isBlank(num)) {
      return null;
    }
    return StringUtils.leftPad(StringUtils.right(num, end), StringUtils.length(num), "*");
  }

  /**
   * 手机号码前三后四脱敏
   *
   * @param mobile 手机号
   * @return String
   */
  public static String mobileEncrypt(String mobile) {
    if (DataUtils.isEmpty(mobile) || (mobile.length() != ELEVEN)) {
      return mobile;
    }
    return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
  }

  /**
   * 身份证前三后四脱敏
   *
   * @param id 证件号码
   * @return String
   */
  public static String idEncrypt(String id) {
    if (DataUtils.isEmpty(id) || (id.length() < EIGHT)) {
      return id;
    }
    return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
  }

  /**
   * 护照前2后3位脱敏，护照一般为8或9位
   *
   * @param id 证件号码
   * @return String
   */
  public static String idPassport(String id) {
    if (DataUtils.isEmpty(id) || (id.length() < EIGHT)) {
      return id;
    }
    return id.substring(0, 2) + new String(new char[id.length() - 5]).replace("\0", "*")
        + id.substring(id.length() - 3);
  }

  /**
   * 证件后几位脱敏
   *
   * @param id            证件号码
   * @param sensitiveSize 位数
   * @return String
   */
  public static String idPassport(String id, int sensitiveSize) {
    if (StringUtils.isBlank(id)) {
      return null;
    }
    int length = StringUtils.length(id);
    return StringUtils.rightPad(StringUtils.left(id, length - sensitiveSize), length, "*");
  }
}
