package com.oner365.monitor.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oner365.api.enums.MisfirePolicyEnum;
import com.oner365.api.enums.TaskStatusEnum;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.monitor.util.CronUtils;

/**
 * 定时任务调度表 nt_sys_task
 *
 * @author liutao
 */
public class SysTaskVo implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 任务ID
   */
  private String id;

  /**
   * 任务名称
   */
  @NotBlank(message = "{monitor.vo.task.taskName.message}")
  private String taskName;

  /**
   * 任务组名
   */
  @NotBlank(message = "{monitor.vo.task.taskGroup.message}")
  private String taskGroup;

  /**
   * 调用目标字符串
   */
  @NotBlank(message = "{monitor.vo.task.invokeTarget.message}")
  private String invokeTarget;

  /**
   * 调用目标参数
   */
  private InvokeParamVo invokeParam;

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
   * 执行任务状态
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

  public SysTaskVo() {
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

  public InvokeParamVo getInvokeParam() {
    return invokeParam;
  }

  public void setInvokeParam(InvokeParamVo invokeParam) {
    this.invokeParam = invokeParam;
  }

  public StatusEnum getExecuteStatus() {
    return executeStatus;
  }

  public void setExecuteStatus(StatusEnum executeStatus) {
    this.executeStatus = executeStatus;
  }

}
