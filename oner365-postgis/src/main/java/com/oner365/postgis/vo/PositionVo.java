package com.oner365.postgis.vo;

import java.io.Serializable;

import com.oner365.postgis.enums.PostgisTypeEnum;

/**
 * Position VO
 * 
 * @author zhaoyong
 */
public class PositionVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private String id;

  /**
   * 名称
   */
  private String positionName;
  
  /**
   * 枚举类型
   */
  private PostgisTypeEnum postgisType;

  /**
   * Geometry Point
   */
  private PointVo point;

  /**
   * Geometry Polygon
   */
  private PolygonVo polygon;
  
  /**
   * Geometry LineString
   */
  private LineStringVo lineString;
  
  /**
   * Geometry MultiPoint
   */
  private MultiPointVo multiPoint;
  
  /**
   * Geometry MultiLineString
   */
  private MultiLineStringVo multiLineString;
  
  /**
   * Geometry MultiPolygon
   */
  private MultiPolygonVo multiPolygon;
  
  public PositionVo() {
    super();
  }

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

  public MultiPointVo getMultiPoint() {
    return multiPoint;
  }

  public void setMultiPoint(MultiPointVo multiPoint) {
    this.multiPoint = multiPoint;
  }

  public MultiLineStringVo getMultiLineString() {
    return multiLineString;
  }

  public void setMultiLineString(MultiLineStringVo multiLineString) {
    this.multiLineString = multiLineString;
  }

  public MultiPolygonVo getMultiPolygon() {
    return multiPolygon;
  }

  public void setMultiPolygon(MultiPolygonVo multiPolygon) {
    this.multiPolygon = multiPolygon;
  }

}
