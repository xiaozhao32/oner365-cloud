package com.oner365.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.common.constants.PublicConstants;

/**
 * 基础权限--角色菜单操作表nt_sys_role_menu_oper
 * 
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_role_menu_oper")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysRoleMenuOper implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号 id
   */
  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
  private String id;

  /**
   * 角色 role_id
   */
  @Column(name = "role_id", nullable = false)
  private String roleId;

  /**
   * 菜单类型 menu_type_id
   */
  @Column(name = "menu_type_id", nullable = false)
  private String menuTypeId;

  /**
   * 菜单 menu_id
   */
  @Column(name = "menu_id", nullable = false)
  private String menuId;

  /**
   * 菜单操作operation_id
   */
  @Column(name = "operation_id", nullable = false)
  private String operationId;

  /**
   * Generate constructor
   */
  public SysRoleMenuOper() {
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

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getMenuTypeId() {
    return menuTypeId;
  }

  public void setMenuTypeId(String menuTypeId) {
    this.menuTypeId = menuTypeId;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public String getOperationId() {
    return operationId;
  }

  public void setOperationId(String operationId) {
    this.operationId = operationId;
  }

  /**
   * toString method
   */
  @Override
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }
}
