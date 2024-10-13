package com.oner365.data.web.entity;

import java.io.Serializable;

/**
 * 网关错误信息对象
 * 
 * @author zhaoyong
 *
 */
public class GatewayError implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Integer code;
  private String path;
  private String method;
  private String message;
  private String result;

  /**
   * 构造方法
   */
  public GatewayError() {
    super();
  }

  /**
   * @return the code
   */
  public Integer getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(Integer code) {
    this.code = code;
  }

  /**
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * @param path the path to set
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * @return the method
   */
  public String getMethod() {
    return method;
  }

  /**
   * @param method the method to set
   */
  public void setMethod(String method) {
    this.method = method;
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

  /**
   * @return the result
   */
  public String getResult() {
    return result;
  }

  /**
   * @param result the result to set
   */
  public void setResult(String result) {
    this.result = result;
  }

}
