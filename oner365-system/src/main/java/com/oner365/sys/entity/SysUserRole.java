package com.oner365.sys.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 基础权限--用户角色表nt_sys_user_role
 * @author liutao
 */
@Entity
@Table(name = "nt_sys_user_role")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号 id
     */
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "uuid")
    private String id;

    /**
     * 角色 role_id
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name = "role_id")
    private SysRole sysRole;

    /**
     * 用户 user_id
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private SysUser sysUser;



    /**
     * Generate constructor
     */
    public SysUserRole() {
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

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }
}
