package com.oner365.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;

/**
 * 基础权限--角色表nt_sys_role
 *
 * @author liutao
 */
public class SysRoleDto implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号 id
   */
  private String id;

  /**
   * 角色标识 role_code
   */
  private String roleCode;

  /**
   * 角色名称 role_name
   */
  private String roleName;

  /**
   * 角色描述 role_des
   */
  private String roleDes;

  /**
   * 状态 status
   */
  private StatusEnum status;

  /**
   * 创建时间 create_time
   */
  private LocalDateTime createTime;

  /**
   * 更新时间 update_time
   */
  private LocalDateTime updateTime;

  /**
   * 菜单id
   */
  private JSONArray menuIds;

  /**
   * 菜单类型
   */
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
