package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StatusEnum;

/**
 * 基础权限--用户部门职位nt_sys_user_job
 *
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_user_job")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysUserJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
    private String id;

    /**
     * 用户 user_id
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private SysUser sysUser;

    /**
     * 职位 job_id
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "job_id")
    private SysJob sysJob;

    /**
     * 排序 position_order
     */
    @Column(name = "position_order", nullable = false, length = 10)
    private Integer positionOrder;

    /**
     * 状态 status
     */
    @Enumerated
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    /**
     * 创建时间 create_time
     */
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间 update_time
     */
    @Column(name = "update_time", updatable = true)
    private LocalDateTime updateTime;

    /**
     * Generate constructor
     */
    public SysUserJob() {
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

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysJob getSysJob() {
        return sysJob;
    }

    public void setSysJob(SysJob sysJob) {
        this.sysJob = sysJob;
    }

    public Integer getPositionOrder() {
        return positionOrder;
    }

    public void setPositionOrder(Integer positionOrder) {
        this.positionOrder = positionOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
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
     * toString method
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

}
