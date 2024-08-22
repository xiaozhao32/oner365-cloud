package com.oner365.postgis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import com.alibaba.fastjson.JSON;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.postgis.enums.PostgisTypeEnum;

/**
 * Position PO
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "t_position")
public class Position implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
  private String id;

  /**
   * 名称
   */
  @Column(name = "position_name", length = 32)
  private String positionName;

  /**
   * 枚举类型
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "postgis_type")
  private PostgisTypeEnum postgisType;

  /**
   * Geometry Point
   */
  @Column(name = "position_point", columnDefinition = "geometry(Point,4326)")
  private Point positionPoint;

  /**
   * Geometry Polygon
   */
  @Column(name = "position_polygon", columnDefinition = "geometry(Polygon,4326)")
  private Polygon positionPolygon;

  /**
   * 创建时间
   */
  @Column(name = "create_time", updatable = false)
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  @Column(name = "update_time", insertable = false)
  private LocalDateTime updateTime;

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

  public Point getPositionPoint() {
    return positionPoint;
  }

  public void setPositionPoint(Point positionPoint) {
    this.positionPoint = positionPoint;
  }

  public Polygon getPositionPolygon() {
    return positionPolygon;
  }

  public void setPositionPolygon(Polygon positionPolygon) {
    this.positionPolygon = positionPolygon;
  }

  public PostgisTypeEnum getPostgisType() {
    return postgisType;
  }

  public void setPostgisType(PostgisTypeEnum postgisType) {
    this.postgisType = postgisType;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

}
