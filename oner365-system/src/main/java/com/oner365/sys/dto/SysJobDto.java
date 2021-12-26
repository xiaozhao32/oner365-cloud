package com.oner365.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.google.common.base.MoreObjects;

/**
 * 职位信息 SysJob
 *
 * @author zhaoyong
 */
public class SysJobDto implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 编号 id
   */
  private String id;

  /**
   * 职位名称 job_name
   */
  private String jobName;

  /**
   * 职位名称 parent_id
   */
  private String parentId;

  /**
   * 职位图标 job_logo
   */
  private String jobLogo;

  /**
   * 职位图标 job_logo_url
   */
  private String jobLogoUrl;

  /**
   * 职位描述 job_info
   */
  private String jobInfo;

  /**
   * 职位排序 job_order
   */
  private Integer jobOrder;

  /**
   * 状态 status
   */
  private String status;

  /**
   * 创建时间 create_time
   */
  private LocalDateTime createTime;

  /**
   * 更新时间 update_time
   */
  private LocalDateTime updateTime;

  /**
   * Generate constructor
   */
  public SysJobDto() {
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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
