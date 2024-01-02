package com.oner365.datasource.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StatusEnum;

/**
 * 订单对象
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "t_order")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Order implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
  private String id;

  /**
   * 订单id
   */
  @Column(name = "order_id", nullable = false)
  private Integer orderId;

  /**
   * 用户id
   */
  @Column(name = "user_id", nullable = false)
  private Integer userId;

  /**
   * 状态
   */
  @Enumerated
  @Column(name = "status")
  private StatusEnum status;

  /**
   * 创建时间
   */
  @Column(name = "create_time")
  private LocalDateTime createTime;

  /**
   * 构造方法
   */
  public Order() {
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
   * @return the orderId
   */
  public Integer getOrderId() {
    return orderId;
  }

  /**
   * @param orderId the orderId to set
   */
  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  /**
   * @return the userId
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /**
   * @return the status
   */
  public StatusEnum getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  /**
   * @return the createTime
   */
  public LocalDateTime getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime the createTime to set
   */
  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

}
