package com.oner365.datasource.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单对象
 * 
 * @author zhaoyong
 *
 */
public class OrderDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private String id;

  /**
   * 订单id
   */
  private Integer orderId;

  /**
   * 用户id
   */
  private Integer userId;

  /**
   * 状态
   */
  private String status;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  public OrderDto() {
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
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
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
