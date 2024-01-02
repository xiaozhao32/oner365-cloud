package com.oner365.elasticsearch.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.oner365.data.commons.util.DateUtil;
import com.oner365.elasticsearch.dto.GeoPoint;

/**
 * 坐标信息
 *
 * @author zhaoyong
 */
@Document(indexName = "samplelocation")
@Setting(refreshInterval = "-1")
public class SampleLocation implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @Id
  private String id;

  /**
   * 坐标名称
   */
  @Field(name = "location_name", type = FieldType.Keyword)
  private String locationName;

  /**
   * 坐标信息
   */
  @GeoPointField
  @Field(name = "location_point")
  private GeoPoint locationPoint;

  /**
   * 坐标描述
   */
  @Field(name = "location_desc", type = FieldType.Keyword)
  private String locationDesc;

  /**
   * 创建时间
   */
  @Field(name = "create_time", type = FieldType.Date, pattern = DateUtil.FULL_TIME_FORMAT)
  private Date createTime;

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
