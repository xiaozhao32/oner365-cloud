package com.oner365.postgis.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import com.oner365.postgis.enums.PostgisTypeEnum;

/**
 * Position DTO
 * 
 * @author zhaoyong
 */
public class PositionDto implements Serializable {

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
  private Point point;
  
  /**
   * Geometry Polygon
   */
  private Polygon polygon;
  
  /**
   * Geometry LineString
   */
  private Polygon lineString;
  
  /**
   * Geometry MultiPoint
   */
  private List<Point> multiPoint;
  
  /**
   * Geometry MultiLineString
   */
  private List<Polygon> multiLineString;
  
  /**
   * Geometry MultiPolygon
   */
  private List<Polygon> multiPolygon;
  
  /**
   * 创建时间
   */
  private LocalDateTime createTime;
  
  /**
   * 更新时间
   */
  private LocalDateTime updateTime;
  
  public PositionDto() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Point getPoint() {
    return point;
  }

  public void setPoint(Point point) {
    this.point = point;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public Polygon getPolygon() {
    return polygon;
  }

  public void setPolygon(Polygon polygon) {
    this.polygon = polygon;
  }

  public PostgisTypeEnum getPostgisType() {
    return postgisType;
  }

  public void setPostgisType(PostgisTypeEnum postgisType) {
    this.postgisType = postgisType;
  }

  public Polygon getLineString() {
    return lineString;
  }

  public void setLineString(Polygon lineString) {
    this.lineString = lineString;
  }

  public List<Point> getMultiPoint() {
    return multiPoint;
  }

  public void setMultiPoint(List<Point> multiPoint) {
    this.multiPoint = multiPoint;
  }

  public List<Polygon> getMultiLineString() {
    return multiLineString;
  }

  public void setMultiLineString(List<Polygon> multiLineString) {
    this.multiLineString = multiLineString;
  }

  public List<Polygon> getMultiPolygon() {
    return multiPolygon;
  }

  public void setMultiPolygon(List<Polygon> multiPolygon) {
    this.multiPolygon = multiPolygon;
  }

}
