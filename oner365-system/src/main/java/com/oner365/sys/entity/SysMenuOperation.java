package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.sys.dto.SysMenuOperationDto;

/**
 * 菜单操作对象
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_operation")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenuOperation implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "uuid")
  private String id;

  @Column(name = "operation_name", nullable = false, length = 32)
  private String operationName;

  @Column(name = "operation_type", nullable = false, length = 32)
  private String operationType;

  @Column(name = "status", nullable = false, length = 8)
  private String status;

  /**
   * 创建时间 create_time
   */
  @Column(name = "create_time", updatable = false)
  private LocalDateTime createTime;

  /**
   * 更新时间 update_time
   */
  @Column(name = "update_time", insertable = false)
  private LocalDateTime updateTime;

  /**
   * Constructor
   */
  public SysMenuOperation() {
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

  /**
   * 转换对象
   * 
   * @return SysMenuOperationDto
   */
  public SysMenuOperationDto toDto() {
    SysMenuOperationDto result = new SysMenuOperationDto();
    result.setId(this.getId());
    result.setCreateTime(this.getCreateTime());
    result.setOperationName(this.getOperationName());
    result.setOperationType(this.getOperationType());
    result.setStatus(this.getStatus());
    result.setUpdateTime(this.getUpdateTime());
    return result;
  }

}
