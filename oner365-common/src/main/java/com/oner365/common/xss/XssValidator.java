package com.oner365.common.xss;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.oner365.util.DataUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义xss校验注解实现
 *
 * @author zhaoyong
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

  private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (DataUtils.isEmpty(value)) {
      return true;
    }
    return !containsHtml(value);
  }

  /**
   * containsHtml
   *
   * @param value html value
   * @return 是否包含
   */
  public static boolean containsHtml(String value) {
    Pattern pattern = Pattern.compile(HTML_PATTERN);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
