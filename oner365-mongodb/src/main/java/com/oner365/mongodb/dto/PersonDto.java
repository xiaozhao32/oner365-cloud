package com.oner365.mongodb.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Mongodb Person Dto
 * 
 * @author zhaoyong
 *
 */
public class PersonDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /** 
   * 主键
   */
  private String id;
  
  /**
   * 姓名
   */
  private String name;
  
  /**
   * 年龄
   */
  private int age;
  
  /**
   * 创建时间
   */
  private Timestamp createTime;
  
  /**
   * 更新时间
   */
  private Timestamp updateTime;
  
  public PersonDto() {
    super();
  }
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getAge() {
    return age;
  }
  public void setAge(int age) {
    this.age = age;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  public Timestamp getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

}
