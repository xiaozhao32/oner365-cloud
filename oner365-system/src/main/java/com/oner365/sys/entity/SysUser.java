package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.sys.enums.SysUserSexEnum;
import com.oner365.sys.enums.SysUserTypeEnum;

/**
 * 系统用户对象
 *
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
    private String id;

    @Column(name = "user_code", length = 32)
    private String userCode;

    @Column(name = "user_name", nullable = false, length = 32)
    private String userName;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @Column(name = "real_name", length = 32)
    private String realName;

    @Column(name = "avatar")
    private String avatar;

    @Enumerated
    @Column(name = "sex", length = 1)
    private SysUserSexEnum sex;

    @Enumerated
    @Column(name = "status", nullable = false)
    private StatusEnum status;

    @Column(name = "last_time")
    private LocalDateTime lastTime;

    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "last_ip", length = 32)
    private String lastIp;

    @Column(name = "email", length = 32)
    private String email;

    @Column(name = "phone", length = 32)
    private String phone;

    @Column(name = "id_card", length = 32)
    private String idCard;

    @Column(name = "is_admin", length = 8)
    private String isAdmin;

    @Column(name = "default_password", length = 32)
    private String defaultPassword;

    @Enumerated
    @Column(name = "active_status", length = 10)
    private StatusEnum activeStatus;

    @Enumerated
    @Column(name = "user_type", length = 2)
    private SysUserTypeEnum userType;

    @Column(name = "id_type", length = 2)
    private String idType;

    @Column(name = "remark")
    private String remark;

    @Transient
    private List<String> roles = new ArrayList<>();

    @Transient
    private List<String> roleNameList = new ArrayList<>();

    @Transient
    private List<String> jobs = new ArrayList<>();

    @Transient
    private List<String> jobNameList = new ArrayList<>();

    @Transient
    private List<String> orgs = new ArrayList<>();

    @Transient
    private List<String> orgNameList = new ArrayList<>();

    public SysUser() {
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
     * @return the userCode
     */
    public String getUserCode() {
        return userCode;
    }

    /**
     * @param userCode the userCode to set
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
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
     * @return the lastTime
     */
    public LocalDateTime getLastTime() {
        return lastTime;
    }

    /**
     * @param lastTime the lastTime to set
     */
    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
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
     * @return the lastIp
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * @param lastIp the lastIp to set
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the idCard
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard the idCard to set
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return the isAdmin
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return the defaultPassword
     */
    public String getDefaultPassword() {
        return defaultPassword;
    }

    /**
     * @param defaultPassword the defaultPassword to set
     */
    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    /**
     * @return the activeStatus
     */
    public StatusEnum getActiveStatus() {
        return activeStatus;
    }

    /**
     * @param activeStatus the activeStatus to set
     */
    public void setActiveStatus(StatusEnum activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * @return the userType
     */
    public SysUserTypeEnum getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(SysUserTypeEnum userType) {
        this.userType = userType;
    }

    /**
     * @return the idType
     */
    public String getIdType() {
        return idType;
    }

    /**
     * @param idType the idType to set
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the sex
     */
    public SysUserSexEnum getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(SysUserSexEnum sex) {
        this.sex = sex;
    }

    /**
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the jobs
     */
    public List<String> getJobs() {
        return jobs;
    }

    /**
     * @param jobs the jobs to set
     */
    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    /**
     * @return the orgs
     */
    public List<String> getOrgs() {
        return orgs;
    }

    /**
     * @param orgs the orgs to set
     */
    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    /**
     * @return the roleNameList
     */
    public List<String> getRoleNameList() {
        return roleNameList;
    }

    /**
     * @param roleNameList the roleNameList to set
     */
    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    /**
     * @return the jobNameList
     */
    public List<String> getJobNameList() {
        return jobNameList;
    }

    /**
     * @param jobNameList the jobNameList to set
     */
    public void setJobNameList(List<String> jobNameList) {
        this.jobNameList = jobNameList;
    }

    /**
     * @return the orgNameList
     */
    public List<String> getOrgNameList() {
        return orgNameList;
    }

    /**
     * @param orgNameList the orgNameList to set
     */
    public void setOrgNameList(List<String> orgNameList) {
        this.orgNameList = orgNameList;
    }

}
