package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作对象
 * 
 * @author zhaoyong
 */
public class SysMenuOperationVo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 编号 id
   */
  private String id;

  /**
   * 操作名称
   */
  private String operationName;

  /**
   * 操作类型
   */
  private String operationType;

  /**
   * 状态 status
   */
  private String status;

  /**
   * 创建时间 create_time
   */
  private LocalDateTime createTime;

  /**
   * 更新时间 update_time
   */
  private LocalDateTime updateTime;

  /**
   * Constructor
   */
  public SysMenuOperationVo() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }

  public String getOperationType() {
    return operationType;
  }

  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
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

}
