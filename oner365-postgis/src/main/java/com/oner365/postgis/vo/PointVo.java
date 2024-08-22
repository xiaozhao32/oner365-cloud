package com.oner365.postgis.vo;

import java.io.Serializable;

/**
 * Point VO
 * 
 * @author zhaoyong
 */
public class PointVo implements Serializable {

  private static final long serialVersionUID = 1L;

  private double x;
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
