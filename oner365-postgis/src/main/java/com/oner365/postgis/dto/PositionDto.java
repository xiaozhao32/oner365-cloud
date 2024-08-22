package com.oner365.postgis.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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

  private String id;
  
  private String positionName;
  
  private PostgisTypeEnum postgisType;

  private Point point;
  
  private Polygon polygon;
  
  private Polygon lineString;
  
  private LocalDateTime createTime;
  
  private LocalDateTime updateTime;

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

}
