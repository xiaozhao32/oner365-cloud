package com.oner365.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 基础权限--角色惨淡表nt_sys_role_menu
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_role_menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @Id
    private String id;

    /**
     * 菜单类型 menu_type_id
     */
    @Column(name = "menu_type_id" ,nullable = false)
    private String menuTypeId;

    /**
     * 角色 role_id
     */
    @Column(name = "role_id" ,nullable = false)
    private String roleId;

    /**
     * 菜单 menu_id
     */
    @Column(name = "menu_id")
    private String menuId;



    /**
     * Generate constructor
     */
    public SysRoleMenu() {
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

    /**
     * toString method
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}
