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
import com.oner365.data.commons.constants.PublicConstants;

/**
 * 系统日志对象
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_log")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysLog implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
  private String id;

  @Column(name = "operation_ip", length = 32)
  private String operationIp;

  @Column(name = "method_name", length = 8)
  private String methodName;

  @Column(name = "operation_name")
  private String operationName;

  @Column(name = "operation_path")
  private String operationPath;

  @Column(name = "operation_context")
  private String operationContext;

  /**
   * 创建时间 create_time
   */
  @Column(name = "create_time", updatable = false)
  private LocalDateTime createTime;

  /**
   * Constructor
   */
  public SysLog() {
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
   * @return the operationIp
   */
  public String getOperationIp() {
    return operationIp;
  }

  /**
   * @param operationIp the operationIp to set
   */
  public void setOperationIp(String operationIp) {
    this.operationIp = operationIp;
  }

  /**
   * @return the methodName
   */
  public String getMethodName() {
    return methodName;
  }

  /**
   * @param methodName the methodName to set
   */
  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  /**
   * @return the operationName
   */
  public String getOperationName() {
    return operationName;
  }

  /**
   * @param operationName the operationName to set
   */
  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }

  /**
   * @return the operationPath
   */
  public String getOperationPath() {
    return operationPath;
  }

  /**
   * @param operationPath the operationPath to set
   */
  public void setOperationPath(String operationPath) {
    this.operationPath = operationPath;
  }

  /**
   * @return the operationContext
   */
  public String getOperationContext() {
    return operationContext;
  }

  /**
   * @param operationContext the operationContext to set
   */
  public void setOperationContext(String operationContext) {
    this.operationContext = operationContext;
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
