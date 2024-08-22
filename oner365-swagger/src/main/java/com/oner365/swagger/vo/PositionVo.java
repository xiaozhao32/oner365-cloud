package com.oner365.swagger.vo;

import java.io.Serializable;

import com.oner365.swagger.enums.PostgisTypeEnum;

/**
 * Position VO
 * 
 * @author zhaoyong
 */
public class PositionVo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;

  private String positionName;
  
  private PostgisTypeEnum postgisType;

  private PointVo point;

  private PolygonVo polygon;
  
  private LineStringVo lineString;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }

  public PointVo getPoint() {
    return point;
  }

  public void setPoint(PointVo point) {
    this.point = point;
  }

  public PolygonVo getPolygon() {
    return polygon;
  }

  public void setPolygon(PolygonVo polygon) {
    this.polygon = polygon;
  }

  public PostgisTypeEnum getPostgisType() {
    return postgisType;
  }

  public void setPostgisType(PostgisTypeEnum postgisType) {
    this.postgisType = postgisType;
  }

  public LineStringVo getLineString() {
    return lineString;
  }

  public void setLineString(LineStringVo lineString) {
    this.lineString = lineString;
  }

}
