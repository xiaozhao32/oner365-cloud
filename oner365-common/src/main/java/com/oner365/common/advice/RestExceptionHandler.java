package com.oner365.common.advice;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oner365.common.ResponseData;
import com.oner365.common.enums.ResultEnum;

/**
 * 全局异常信息
 * 
 * @author zhaoyong
 *
 */
@RestControllerAdvice
public class RestExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  /**
   * 验证异常信息处理
   * 
   * @param e BindException
   * @return ResponseData
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseData<String> exception(BindException e) {
    LOGGER.error("[验证异常] 异常信息:{}", Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
    return ResponseData.error(ResultEnum.ERROR.getCode(),
        Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
  }

  /**
   * 异常信息处理
   * 
   * @param e Exception
   * @return ResponseData
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseData<String> exception(Exception e) {
    LOGGER.error("[请求异常] 异常信息:{}", e.getMessage(), e);
    return ResponseData.error(ResultEnum.ERROR.getCode(), e.getMessage());
  }
}
