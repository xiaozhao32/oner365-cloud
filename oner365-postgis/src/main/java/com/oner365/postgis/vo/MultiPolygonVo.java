package com.oner365.postgis.vo;

import java.io.Serializable;
import java.util.List;

/**
 * MultiPolygon
 * 
 * @author zhaoyong
 */
public class MultiPolygonVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Polygon List
   */
  private List<PolygonVo> polygons;
  
  public MultiPolygonVo() {
    super();
  }

  public List<PolygonVo> getPolygons() {
    return polygons;
  }

  public void setPolygons(List<PolygonVo> polygons) {
    this.polygons = polygons;
  }

}
