package com.oner365.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单操作对象
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "菜单操作对象")
public class SysMenuOperDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 操作id
   */
  @ApiModelProperty(value = "操作id")
  private String operId;
  
  /**
   * 操作名称
   */
  @ApiModelProperty(value = "操作名称")
  private String operName;
  
  /**
   * 操作类型
   */
  @ApiModelProperty(value = "操作类型")
  private String operType;
  
  public SysMenuOperDto() {
    super();
  }

  public String getOperId() {
    return operId;
  }

  public void setOperId(String operId) {
    this.operId = operId;
  }

  public String getOperName() {
    return operName;
  }

  public void setOperName(String operName) {
    this.operName = operName;
  }

  public String getOperType() {
    return operType;
  }

  public void setOperType(String operType) {
    this.operType = operType;
  }
  
}
