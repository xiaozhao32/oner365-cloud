package com.oner365.swagger.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 职位信息 SysJob
 *
 * @author zhaoyong
 */
@ApiModel(value = "职位信息")
public class SysJobVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 职位名称 job_name
     */
    @ApiModelProperty(value = "职位名称", required = true)
    @NotBlank(message = "{system.vo.job.jobName.message}")
    private String jobName;

    /**
     * 职位上级 parent_id
     */
    @ApiModelProperty(value = "职位上级id")
    private String parentId;

    /**
     * 职位图标 job_logo
     */
    @ApiModelProperty(value = "图标名称")
    private String jobLogo;

    /**
     * 职位图标 job_logo_url
     */
    @ApiModelProperty(value = "图标地址")
    private String jobLogoUrl;

    /**
     * 职位描述 job_info
     */
    @ApiModelProperty(value = "职位描述")
    private String jobInfo;

    /**
     * 职位排序 job_order
     */
    @ApiModelProperty(value = "职位排序")
    private Integer jobOrder;

    /**
     * 状态 status
     */
    @ApiModelProperty(value = "状态")
    private StatusEnum status;

    /**
     * 创建时间 create_time
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间 update_time
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * Generate constructor
     */
    public SysJobVo() {
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

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getJobLogo() {
        return jobLogo;
    }

    public void setJobLogo(String jobLogo) {
        this.jobLogo = jobLogo;
    }

    public String getJobLogoUrl() {
        return jobLogoUrl;
    }

    public void setJobLogoUrl(String jobLogoUrl) {
        this.jobLogoUrl = jobLogoUrl;
    }

    public String getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }

    public Integer getJobOrder() {
        return jobOrder;
    }

    public void setJobOrder(Integer jobOrder) {
        this.jobOrder = jobOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
