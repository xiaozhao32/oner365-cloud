package com.oner365.monitor.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.MoreObjects;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.entity.InvokeParam;
import com.oner365.monitor.util.CronUtils;
import com.oner365.util.DataUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 定时任务调度表 nt_sys_task
 *
 * @author liutao
 */
@ApiModel(value = "定时任务")
public class SysTaskDto implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  @ApiModelProperty(value = "主键")
  private String id;

  /**
   * 任务名称
   */
  @ApiModelProperty(value = "任务名称", required = true)
  private String taskName;

  /**
   * 任务组名
   */
  @ApiModelProperty(value = "任务组", required = true)
  private String taskGroup;

  /**
   * 调用目标字符串
   */
  @ApiModelProperty(value = "调用目标", required = true)
  private String invokeTarget;

  /**
   * 调用目标参数
   */
  @ApiModelProperty(value = "目标参数")
  private InvokeParam invokeParam;

  /**
   * cron执行表达式
   */
  @ApiModelProperty(value = "执行表达式")
  private String cronExpression;

  /**
   * cron计划策略
   */
  @ApiModelProperty(value = "计划策略")
  private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

  /**
   * 是否并发执行（0允许 1禁止）
   */
  @ApiModelProperty(value = "是否并发执行（0允许 1禁止）")
  private String concurrent;

  /**
   * 任务状态（0正常 1暂停）
   */
  @ApiModelProperty(value = "任务状态（0正常 1暂停）")
  private String status;

  /**
   * 执行任务状态（0正在执行 1执行完成）
   */
  @ApiModelProperty(value = "执行任务状态（0正在执行 1执行完成）")
  private String executeStatus;

  /**
   * 备注
   */
  @ApiModelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ApiModelProperty(value = "创建人")
  private String createUser;

  /**
   * 创建时间
   */
  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 更新时间
   */
  @ApiModelProperty(value = "更新时间")
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

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  public Date getNextValidTime() {
    if (!DataUtils.isEmpty(cronExpression)) {
      return CronUtils.getNextExecution(cronExpression);
    }
    return null;
  }

  public String getMisfirePolicy() {
    return misfirePolicy;
  }

  public void setMisfirePolicy(String misfirePolicy) {
    this.misfirePolicy = misfirePolicy;
  }

  public String getConcurrent() {
    return concurrent;
  }

  public void setConcurrent(String concurrent) {
    this.concurrent = concurrent;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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

  public InvokeParam getInvokeParam() {
    return invokeParam;
  }

  public void setInvokeParam(InvokeParam invokeParam) {
    this.invokeParam = invokeParam;
  }

  public String getExecuteStatus() {
    return executeStatus;
  }

  public void setExecuteStatus(String executeStatus) {
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
