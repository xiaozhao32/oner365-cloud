package com.oner365.elasticsearch.vo;

import java.io.Serializable;
import java.util.Date;

import com.oner365.elasticsearch.dto.GeoPoint;

/**
 * 坐标信息
 * 
 * @author zhaoyong
 *
 */
public class SampleLocationVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private String id;

  /**
   * 坐标名称
   */
  private String locationName;

  /**
   * 坐标信息
   */
  private GeoPoint locationPoint;

  /**
   * 坐标描述
   */
  private String locationDesc;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 构造方法
   */
  public SampleLocationVo() {
    super();
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the locationName
   */
  public String getLocationName() {
    return locationName;
  }

  /**
   * @param locationName the locationName to set
   */
  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }

  /**
   * @return the locationPoint
   */
  public GeoPoint getLocationPoint() {
    return locationPoint;
  }

  /**
   * @param locationPoint the locationPoint to set
   */
  public void setLocationPoint(GeoPoint locationPoint) {
    this.locationPoint = locationPoint;
  }

  /**
   * @return the locationDesc
   */
  public String getLocationDesc() {
    return locationDesc;
  }

  /**
   * @param locationDesc the locationDesc to set
   */
  public void setLocationDesc(String locationDesc) {
    this.locationDesc = locationDesc;
  }

  /**
   * @return the createTime
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime the createTime to set
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
