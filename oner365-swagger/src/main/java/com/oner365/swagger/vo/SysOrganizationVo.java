package com.oner365.swagger.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 机构表 - nt_sys_organization
 *
 * @author liutao
 *
 */
@ApiModel(value = "机构")
public class SysOrganizationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 机构名称 org_name
     */
    @ApiModelProperty(value = "机构名称", required = true)
    @NotBlank(message = "{system.vo.org.orgName.message}")
    private String orgName;

    /**
     * 机构名称 ancestors
     */
    @ApiModelProperty(value = "机构编号")
    private String ancestors;

    /**
     * 机构代码 org_code
     */
    @ApiModelProperty(value = "机构代码", required = true)
    @NotBlank(message = "{system.vo.org.orgCode.message}")
    private String orgCode;

    /**
     * 机构统一社会信用代码 org_credit_code
     */
    @ApiModelProperty(value = "信用代码")
    private String orgCreditCode;

    /**
     * 机构行政划区代码 org_area_code
     */
    @ApiModelProperty(value = "行政区域代码")
    private String orgAreaCode;

    /**
     * 机构类型 org_type
     */
    @ApiModelProperty(value = "机构类型")
    private String orgType;

    /**
     * 机构图标 org_logo
     */
    @ApiModelProperty(value = "图标")
    private String orgLogo;

    /**
     * 机构图标地址 org_logo_url
     */
    @ApiModelProperty(value = "图标地址")
    private String orgLogoUrl;

    /**
     * 机构排序 org_order
     */
    @ApiModelProperty(value = "排序")
    private Integer orgOrder;

    /**
     * 机构父级机构编号 parent_id
     */
    @ApiModelProperty(value = "上级id")
    private String parentId;

    /**
     * 状态 status
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "{system.vo.org.status.message}")
    private StatusEnum status;

    /**
     * 更新时间update_time
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建时间 create_time
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime createTime;

    /**
     * 业务负责人
     */
    @ApiModelProperty(value = "业务负责人")
    private String businessName;

    /**
     * 业务负责人电话
     */
    @ApiModelProperty(value = "业务负责人电话")
    private String businessPhone;

    /**
     * 技术负责人
     */
    @ApiModelProperty(value = "技术负责人")
    private String technicalName;

    /**
     * 技术负责人电话
     */
    @ApiModelProperty(value = "技术负责人电话")
    private String technicalPhone;

    /**
     * 技术负责人电话
     */
    @ApiModelProperty(value = "账号id")
    private String createUser;

    /**
     * 数据源
     */
    @ApiModelProperty(value = "数据源")
    private DataSourceConfigVo dataSourceConfigVo;

    private List<SysOrganizationVo> children = new ArrayList<>();

    /**
     * Generate constructor
     */
    public SysOrganizationVo() {
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
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(StatusEnum status) {
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

    public DataSourceConfigVo getDataSourceConfigVo() {
        return dataSourceConfigVo;
    }

    public void setDataSourceConfigVo(DataSourceConfigVo dataSourceConfigVo) {
        this.dataSourceConfigVo = dataSourceConfigVo;
    }

    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

    public List<SysOrganizationVo> getChildren() {
        return children;
    }

    public void setChildren(List<SysOrganizationVo> children) {
        this.children = children;
    }

}
