package com.oner365.gateway.constants;

import java.io.Serializable;

import com.oner365.gateway.enums.ResultEnum;

/**
 * 服务响应对象
 *
 * @author zhaoyong
 */
public class ResponseData<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编码 默认正常返回
   */
  private int code;

  /**
   * 提示信息 默认成功
   */
  private String message;

  /**
   * 返回数据结果
   */
  private T result;

  /**
   * 构造方法
   */
  public ResponseData() {
      super();
  }

  /**
   * 返回正确结果
   *
   * @param result 结果
   * @return ResponseData
   */
  public static <T> ResponseData<T> success(T result) {
      ResponseData<T> response = new ResponseData<>();
      response.setResult(result);
      response.setCode(ResultEnum.SUCCESS.getCode());
      response.setMessage(ResultEnum.SUCCESS.getName());
      return response;
  }
  
  /**
   * 返回错误结果
   *
   * @param message 消息
   * @return ResponseData
   */
  public static <T extends Serializable> ResponseData<T> error(String message) {
      ResponseData<T> response = new ResponseData<>();
      response.setCode(ResultEnum.ERROR.getCode());
      response.setMessage(message);
      return response;
  }

  /**
   * 返回错误结果
   *
   * @param code    编码
   * @param message 消息
   * @return ResponseData
   */
  public static <T extends Serializable> ResponseData<T> error(int code, String message) {
      ResponseData<T> response = new ResponseData<>();
      response.setCode(code);
      response.setMessage(message);
      return response;
  }

  /**
   * @return the code
   */
  public int getCode() {
      return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(int code) {
      this.code = code;
  }

  /**
   * @return the result
   */
  public T getResult() {
      return result;
  }

  /**
   * @param result the result to set
   */
  public void setResult(T result) {
      this.result = result;
  }

  /**
   * @return the message
   */
  public String getMessage() {
      return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
      this.message = message;
  }

}
