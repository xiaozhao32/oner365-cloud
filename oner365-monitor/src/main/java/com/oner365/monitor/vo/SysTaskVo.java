package com.oner365.monitor.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.entity.InvokeParam;
import com.oner365.monitor.util.CronUtils;
import com.oner365.util.DataUtils;

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
  private InvokeParam invokeParam;

  /**
   * cron执行表达式
   */
  private String cronExpression;

  /**
   * cron计划策略
   */
  private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

  /**
   * 是否并发执行（0允许 1禁止）
   */
  private String concurrent;

  /**
   * 任务状态（0正常 1暂停）
   */
  private String status;

  /**
   * 执行任务状态（0正在执行 1执行完成）
   */
  private String executeStatus;

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

}
