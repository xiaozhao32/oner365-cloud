package com.oner365.api.rabbitmq.dto;

import java.io.Serializable;
import java.util.Date;

import com.google.common.base.MoreObjects;
import com.oner365.api.enums.MisfirePolicyEnum;
import com.oner365.api.enums.TaskStatusEnum;
import com.oner365.common.enums.StatusEnum;

/**
 * 定时任务调度表 nt_sys_task
 *
 * @author liutao
 */
public class SysTaskDto implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  private String id;

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
   * 调用目标参数
   */
  private InvokeParamDto invokeParam;

  /**
   * cron执行表达式
   */
  private String cronExpression;

  /**
   * cron计划策略
   */
  private MisfirePolicyEnum misfirePolicy = MisfirePolicyEnum.DEFAULT;

  /**
   * 是否并发执行（0允许 1禁止）
   */
  private String concurrent;

  /**
   * 任务状态（1正常 0暂停）
   */
  private TaskStatusEnum status;

  /**
   * 执行任务状态（0正在执行 1执行完成）
   */
  private StatusEnum executeStatus;

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

  public SysTaskDto() {
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

  public String getCronExpression() {
    return cronExpression;
  }

  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  public MisfirePolicyEnum getMisfirePolicy() {
    return misfirePolicy;
  }

  public void setMisfirePolicy(MisfirePolicyEnum misfirePolicy) {
    this.misfirePolicy = misfirePolicy;
  }

  public String getConcurrent() {
    return concurrent;
  }

  public void setConcurrent(String concurrent) {
    this.concurrent = concurrent;
  }

  public TaskStatusEnum getStatus() {
    return status;
  }

  public void setStatus(TaskStatusEnum status) {
    this.status = status;
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

  public InvokeParamDto getInvokeParam() {
    return invokeParam;
  }

  public void setInvokeParam(InvokeParamDto invokeParam) {
    this.invokeParam = invokeParam;
  }

  public StatusEnum getExecuteStatus() {
    return executeStatus;
  }

  public void setExecuteStatus(StatusEnum executeStatus) {
    this.executeStatus = executeStatus;
  }

  /**
   * toString Method
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("id", id).toString();
  }

}
