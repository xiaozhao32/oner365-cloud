package com.oner365.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.api.enums.TaskStatusEnum;
import com.oner365.common.constants.PublicConstants;

/**
 * 定时任务调度日志表 nt_sys_task_log
 *
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_task_log")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysTaskLog implements Serializable {
  private static final long serialVersionUID = 1L;

  /** ID */
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
  private String id;

  /** 任务名称 */
  @Column(name = "task_name", nullable = false, length = 64)
  private String taskName;

  /** 任务组名 */
  @Column(name = "task_group", nullable = false, length = 64)
  private String taskGroup;

  /** 调用目标字符串 */
  @Column(name = "invoke_target", nullable = false, length = 500)
  private String invokeTarget;

  /** 日志信息 */
  @Column(name = "task_message", length = 500)
  private String taskMessage;

  /** 执行状态 */
  @Enumerated
  @Column(name = "status", length = 1)
  private TaskStatusEnum status;

  /** 异常信息 */
  @Column(name = "exception_info", length = 2000)
  private String exceptionInfo;

  /** 开始时间 */
  @Column(name = "start_time")
  private Date startTime;

  /** 停止时间 */
  @Column(name = "stop_time")
  private Date stopTime;

  /**
   * 执行任务服务ip
   */
  @Column(name = "execute_ip", length = 64)
  private String executeIp;

  /**
   * 执行任务服务名称
   */
  @Column(name = "execute_server_name", length = 64)
  private String executeServerName;

  /** 备注 */
  @Column(name = "remark", length = 500)
  private String remark;

  /** 创建人 */
  @Column(name = "create_user", length = 32)
  private String createUser;

  /** 创建时间 */
  @Column(name = "create_time", updatable = false)
  private Date createTime;

  /** 更新时间 */
  @Column(name = "update_time", updatable = true)
  private Date updateTime;

  public SysTaskLog() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskGroup() {
    return taskGroup;
  }

  public void setTaskGroup(String taskGroup) {
    this.taskGroup = taskGroup;
  }

  public String getInvokeTarget() {
    return invokeTarget;
  }

  public void setInvokeTarget(String invokeTarget) {
    this.invokeTarget = invokeTarget;
  }

  public String getTaskMessage() {
    return taskMessage;
  }

  public void setTaskMessage(String taskMessage) {
    this.taskMessage = taskMessage;
  }

  public TaskStatusEnum getStatus() {
    return status;
  }

  public void setStatus(TaskStatusEnum status) {
    this.status = status;
  }

  public String getExceptionInfo() {
    return exceptionInfo;
  }

  public void setExceptionInfo(String exceptionInfo) {
    this.exceptionInfo = exceptionInfo;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getStopTime() {
    return stopTime;
  }

  public void setStopTime(Date stopTime) {
    this.stopTime = stopTime;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getExecuteIp() {
    return executeIp;
  }

  public void setExecuteIp(String executeIp) {
    this.executeIp = executeIp;
  }

  public String getExecuteServerName() {
    return executeServerName;
  }

  public void setExecuteServerName(String executeServerName) {
    this.executeServerName = executeServerName;
  }

}
