package com.oner365.cassandra.entity;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * 实体对象
 * 
 * @author zhaoyong
 *
 */
@Table("emp")
public class Employee implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @PrimaryKey("emp_id")
  private Integer id;
  
  /**
   * 名称
   */
  @Column("emp_name")
  private String name;
  
  /**
   * 城市
   */
  @Column("emp_city")
  private String city;
  
  /**
   * 电话
   */
  @Column("emp_phone")
  private Long phone;
  
  /**
   * 薪水
   */
  @Column("emp_sal")
  private Long salary;
  
  /**
   * 构造方法
   */
  public Employee() {
    super();
  }
  
  /**
   * @return the id
   */
  public Integer getId() {
    return id;
  }
  /**
   * @param id the id to set
   */
  public void setId(Integer id) {
    this.id = id;
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }
  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }
  /**
   * @return the phone
   */
  public Long getPhone() {
    return phone;
  }
  /**
   * @param phone the phone to set
   */
  public void setPhone(Long phone) {
    this.phone = phone;
  }
  /**
   * @return the salary
   */
  public Long getSalary() {
    return salary;
  }
  /**
   * @param salary the salary to set
   */
  public void setSalary(Long salary) {
    this.salary = salary;
  }
}
