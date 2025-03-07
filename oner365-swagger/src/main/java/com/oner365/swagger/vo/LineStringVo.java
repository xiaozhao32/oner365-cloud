package com.oner365.swagger.vo;

import java.io.Serializable;
import java.util.List;

/**
 * LineString
 * 
 * @author zhaoyong
 */
public class LineStringVo implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<PointVo> points;

  public List<PointVo> getPoints() {
    return points;
  }

  public void setPoints(List<PointVo> points) {
    this.points = points;
  }
}
