package com.oner365.swagger.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.MoreObjects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基础权限--角色表nt_sys_role
 *
 * @author liutao
 */
@ApiModel(value = "角色信息")
public class SysRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 角色标识 role_code
     */
    @ApiModelProperty(value = "角色标识")
    private String roleCode;

    /**
     * 角色名称 role_name
     */
    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    /**
     * 角色描述 role_des
     */
    @ApiModelProperty(value = "角色描述")
    private String roleDes;

    /**
     * 状态 status
     */
    @ApiModelProperty(value = "状态")
    private String status;

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
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private JSONArray menuIds;
    
    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    /**
     * Generate constructor
     */
    public SysRoleDto() {
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

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDes() {
        return roleDes;
    }

    public void setRoleDes(String roleDes) {
        this.roleDes = roleDes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

    public JSONArray getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(JSONArray menuIds) {
        this.menuIds = menuIds;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
}
