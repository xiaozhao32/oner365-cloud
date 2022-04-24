package com.oner365.api.rabbitmq.dto;

import java.io.Serializable;
import java.util.Date;

import com.oner365.api.enums.TaskStatusEnum;

/**
 * SysTaskLog
 * 
 * @author zhaoyong
 *
 */
public class SysTaskLogDto implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;
  
  /**
   * 任务id
   */
  private String taskId;

  /**
   * 任务名称
   */
  private String taskName;

  /**
   * 任务组名
   */
  private String taskGroup;

  /**
   * 调用目标字符串
   */
  private String invokeTarget;

  /**
   * 日志信息
   */
  private String taskMessage;

  /**
   * 执行状态
   */
  private TaskStatusEnum status;

  /**
   * 异常信息
   */
  private String exceptionInfo;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 停止时间
   */
  private Date stopTime;

  /**
   * 执行任务服务ip
   */
  private String executeIp;

  /**
   * 执行任务服务名称
   */
  private String executeServerName;

  /**
   * 备注
   */
  private String remark;

  /**
   * 创建人
   */
  private String createUser;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;

  public SysTaskLogDto() {
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

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

}
