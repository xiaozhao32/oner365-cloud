package com.oner365.monitor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.api.enums.MisfirePolicyEnum;
import com.oner365.api.enums.TaskStatusEnum;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.monitor.util.CronUtils;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

/**
 * 定时任务调度表 nt_sys_task
 *
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_task")
@TypeDef(name = "json", typeClass = JsonStringType.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 任务ID */
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
    private String id;

    /**
     * 任务名称
     */
    @NotBlank(message = "{monitor.entity.task.taskName.message}")
    @Size(max = 64, message = "{monitor.entity.task.taskName.size}")
    @Column(name = "task_name", nullable = false, length = 64)
    private String taskName;

    /**
     * 任务组名
     */
    @NotBlank(message = "{monitor.entity.task.taskGroup.message}")
    @Size(max = 64, message = "{monitor.entity.task.taskGroup.size}")
    @Column(name = "task_group", nullable = false, length = 64)
    private String taskGroup;

    /**
     * 调用目标字符串
     */
    @NotBlank(message = "{monitor.entity.task.invokeTarget.message}")
    @Size(max = 1000, message = "{monitor.entity.task.invokeTarget.size}")
    @Column(name = "invoke_target", nullable = false, length = 500)
    private String invokeTarget;

    /** 调用目标参数 */
    @Type(type = "json")
    @Column(name = "invoke_param", columnDefinition = "json")
    private InvokeParam invokeParam;

    /** cron执行表达式 */
    @NotBlank(message = "{monitor.entity.task.cronExpression.message}")
    @Size(max = 255, message = "{monitor.entity.task.cronExpression.size}")
    @Column(name = "cron_expression")
    private String cronExpression;

    /** cron计划策略 */
    @Enumerated
    @Column(name = "misfire_policy", length = 20)
    private MisfirePolicyEnum misfirePolicy = MisfirePolicyEnum.DEFAULT;

    /** 是否并发执行（0允许 1禁止） */
    @Column(name = "concurrent", length = 1)
    private String concurrent;

    /** 任务状态（0正常 1暂停） */
    @Enumerated
    @Column(name = "status", length = 1)
    private TaskStatusEnum status;

    /** 执行任务状态（0正在执行 1执行完成） */
    @Enumerated
    @Column(name = "execute_status", length = 1)
    private StatusEnum executeStatus;

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

    public SysTask() {
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

    public InvokeParam getInvokeParam() {
        return invokeParam;
    }

    public void setInvokeParam(InvokeParam invokeParam) {
        this.invokeParam = invokeParam;
    }

    public StatusEnum getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(StatusEnum executeStatus) {
        this.executeStatus = executeStatus;
    }

}
