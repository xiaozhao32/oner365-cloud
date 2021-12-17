package com.oner365.common;

import java.io.Serializable;

import com.oner365.common.enums.ResultEnum;

public class ResponseResult<T extends Serializable> implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 编码 */
  private int code;
  /** 返回对象 */
  private T msg;

  /**
   * 构造方法
   */
  public ResponseResult() {
    super();
  }

  /**
   * 返回正确结果
   *
   * @param result 结果
   * @return ResponseResult
   */
  public static <T extends Serializable> ResponseResult<T> success(T result) {
    ResponseResult<T> response = new ResponseResult<>();
    response.setCode(ResultEnum.SUCCESS.getCode());
    response.setMsg(result);
    return response;
  }

  /**
   * 返回错误结果
   *
   * @param code    编码
   * @param message 消息
   * @return ResponseResult
   */
  @SuppressWarnings("unchecked")
  public static <T extends Serializable> ResponseResult<T> error(int code, String message) {
    ResponseResult<T> response = new ResponseResult<>();
    response.setCode(code);
    response.setMsg((T) message);
    return response;
  }

  /**
   * 返回错误结果
   *
   * @param message 消息
   * @return ResponseResult
   */
  @SuppressWarnings("unchecked")
  public static <T extends Serializable> ResponseResult<T> error(String message) {
    ResponseResult<T> response = new ResponseResult<>();
    response.setCode(ResultEnum.ERROR.getCode());
    response.setMsg((T) message);
    return response;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getMsg() {
    return msg;
  }

  public void setMsg(T msg) {
    this.msg = msg;
  }

}
