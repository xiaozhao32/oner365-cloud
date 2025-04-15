package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录数据对象
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "登录信息")
public class LoginUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    @ApiModelProperty(value = "Authorization Token")
    private String accessToken;
    
    /**
     * token有效期
     */
    @ApiModelProperty(value = "Token 有效期(秒)")
    private Long expireTime;
    
    /**
     * 账号id
     */
    @ApiModelProperty(value = "账号id")
    private String userId;
    
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "姓名")
    private String realName;
    
    /**
     * 是否管理员
     */
    @ApiModelProperty(value = "是否管理员")
    private String isAdmin;
    
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;
    
    /**
     * 角色信息
     */
    @ApiModelProperty(value = "角色信息")
    private List<String> roles = new ArrayList<>();
    
    /**
     * 职位信息
     */
    @ApiModelProperty(value = "职位信息")
    private List<String> jobs = new ArrayList<>();
    
    /**
     * 机构信息
     */
    @ApiModelProperty(value = "机构信息")
    private List<String> orgs = new ArrayList<>();

    /**
     * 构造方法
     */
    public LoginUserDto() {
        super();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getJobs() {
        return jobs;
    }

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public List<String> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<String> orgs) {
        this.orgs = orgs;
    }

}
