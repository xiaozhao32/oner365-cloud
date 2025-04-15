package com.oner365.swagger.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Point VO
 * 
 * @author zhaoyong
 */
@ApiModel(value = "点")
public class PointVo implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "x")
  private double x;
  
  @ApiModelProperty(value = "y")
  private double y;

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

}
