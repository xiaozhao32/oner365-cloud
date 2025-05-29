package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.sys.enums.SysUserSexEnum;
import com.oner365.sys.enums.SysUserTypeEnum;

/**
 * 系统用户
 *
 * @author zhaoyong
 */
public class SysUserVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户标识
     */
    private String userCode;

    /**
     * 账号
     */
    @NotBlank(message = "{system.vo.loginUser.userName.message}")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "{system.vo.loginUser.password.message}")
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private SysUserSexEnum sex;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后登录ip
     */
    private String lastIp;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 是否管理员
     */
    private String isAdmin;

    /**
     * 默认密码
     */
    private String defaultPassword;

    /**
     * 状态
     */
    private StatusEnum activeStatus;

    /**
     * 用户类型
     */
    private SysUserTypeEnum userType;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 备注
     */
    private String remark;

    private List<String> roles = new ArrayList<>();

    private List<String> roleNameList = new ArrayList<>();

    private List<String> jobs = new ArrayList<>();

    private List<String> jobNameList = new ArrayList<>();

    private List<String> orgs = new ArrayList<>();

    private List<String> orgNameList = new ArrayList<>();

    public SysUserVo() {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public List<String> getJobNameList() {
        return jobNameList;
    }

    public void setJobNameList(List<String> jobNameList) {
        this.jobNameList = jobNameList;
    }

    public List<String> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

    public List<String> getOrgNameList() {
        return orgNameList;
    }

    public void setOrgNameList(List<String> orgNameList) {
        this.orgNameList = orgNameList;
    }

}
