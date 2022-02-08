package com.oner365.sys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 基础权限--角色表nt_sys_role
 *
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysRole implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号 id
   */
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "uuid")
  private String id;

  /**
   * 角色标识 role_code
   */
  @Column(name = "role_code", nullable = false, length = 64)
  private String roleCode;

  /**
   * 角色名称 role_name
   */
  @Column(name = "role_name", nullable = false, length = 32)
  private String roleName;

  /**
   * 角色描述 role_des
   */
  @Column(name = "role_des", length = 32)
  private String roleDes;

  /**
   * 状态 status
   */
  @Column(name = "status", nullable = false, length = 32)
  private String status;

  /**
   * 创建时间 create_time
   */
  @Column(name = "create_time", updatable = false)
  private LocalDateTime createTime;

  /**
   * 更新时间 update_time
   */
  @Column(name = "update_time", insertable = false)
  private LocalDateTime updateTime;

  /**
   * Generate constructor
   */
  public SysRole() {
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

}
