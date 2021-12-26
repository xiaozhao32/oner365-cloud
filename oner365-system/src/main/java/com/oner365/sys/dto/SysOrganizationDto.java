package com.oner365.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;

/**
 * 机构表 - nt_sys_organization
 *
 * @author liutao
 *
 */
public class SysOrganizationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    private String id;

    /**
     * 机构名称 org_name
     */
    private String orgName;

    /**
     * 机构名称 ancestors
     */
    private String ancestors;

    /**
     * 机构代码 org_code
     */
    private String orgCode;

    /**
     * 机构统一社会信用代码 org_credit_code
     */
    private String orgCreditCode;

    /**
     * 机构行政划区代码 org_area_code
     */
    private String orgAreaCode;

    /**
     * 机构类型 org_type
     */
    private String orgType;

    /**
     * 机构图标 org_logo
     */
    private String orgLogo;

    /**
     * 机构图标地址 org_logo_url
     */
    private String orgLogoUrl;

    /**
     * 机构排序 org_order
     */
    private Integer orgOrder;

    /**
     * 机构父级机构编号 parent_id
     */
    private String parentId;

    /**
     * 状态 status
     */
    private String status;

    /**
     * 更新时间update_time
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间 create_time
     */
    private LocalDateTime createTime;

    /**
     * 业务负责人
     */
    private String businessName;

    /**
     * 业务负责人电话
     */
    private String businessPhone;

    /**
     * 技术负责人
     */
    private String technicalName;

    /**
     * 技术负责人电话
     */
    private String technicalPhone;

    /**
     * 技术负责人电话
     */
    private String createUser;

    /**
     * 数据源
     */
    private DataSourceConfigDto dataSourceConfigDto;
    
    private List<SysOrganizationDto> children = new ArrayList<>();

    /**
     * Generate constructor
     */
    public SysOrganizationDto() {
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

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the orgCreditCode
     */
    public String getOrgCreditCode() {
        return orgCreditCode;
    }

    /**
     * @param orgCreditCode the orgCreditCode to set
     */
    public void setOrgCreditCode(String orgCreditCode) {
        this.orgCreditCode = orgCreditCode;
    }

    /**
     * @return the orgAreaCode
     */
    public String getOrgAreaCode() {
        return orgAreaCode;
    }

    /**
     * @param orgAreaCode the orgAreaCode to set
     */
    public void setOrgAreaCode(String orgAreaCode) {
        this.orgAreaCode = orgAreaCode;
    }

    /**
     * @return the orgType
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * @param orgType the orgType to set
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    /**
     * @return the orgLogo
     */
    public String getOrgLogo() {
        return orgLogo;
    }

    /**
     * @param orgLogo the orgLogo to set
     */
    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo;
    }

    /**
     * @return the orgLogoUrl
     */
    public String getOrgLogoUrl() {
        return orgLogoUrl;
    }

    /**
     * @param orgLogoUrl the orgLogoUrl to set
     */
    public void setOrgLogoUrl(String orgLogoUrl) {
        this.orgLogoUrl = orgLogoUrl;
    }

    /**
     * @return the orgOrder
     */
    public Integer getOrgOrder() {
        return orgOrder;
    }

    /**
     * @param orgOrder the orgOrder to set
     */
    public void setOrgOrder(Integer orgOrder) {
        this.orgOrder = orgOrder;
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the updateTime
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * @param businessName the businessName to set
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * @return the businessPhone
     */
    public String getBusinessPhone() {
        return businessPhone;
    }

    /**
     * @param businessPhone the businessPhone to set
     */
    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    /**
     * @return the technicalName
     */
    public String getTechnicalName() {
        return technicalName;
    }

    /**
     * @param technicalName the technicalName to set
     */
    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    /**
     * @return the technicalPhone
     */
    public String getTechnicalPhone() {
        return technicalPhone;
    }

    /**
     * @param technicalPhone the technicalPhone to set
     */
    public void setTechnicalPhone(String technicalPhone) {
        this.technicalPhone = technicalPhone;
    }

    /**
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @return the ancestors
     */
    public String getAncestors() {
        return ancestors;
    }

    /**
     * @param ancestors the ancestors to set
     */
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public DataSourceConfigDto getDataSourceConfigDto() {
        return dataSourceConfigDto;
    }

    public void setDataSourceConfigDto(DataSourceConfigDto dataSourceConfigDto) {
        this.dataSourceConfigDto = dataSourceConfigDto;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

    public List<SysOrganizationDto> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrganizationDto> children) {
        this.children = children;
    }

}
