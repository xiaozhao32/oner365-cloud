package com.oner365.sys.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;

/**
 * 系统菜单对象
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_menu")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenu implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键 id
     */
    @Id
    private String id;

    /**
     * 菜单类别 menu_type_id
     */
    @Column(name = "menu_type_id")
    private String menuTypeId;

    /**
     * 菜单名称 menu_name
     */
    @Column(name = "menu_name", nullable = false, length = 32)
    private String menuName;

    /**
     * 别称 another_name
     */
    @Column(name = "another_name", length = 32)
    private String anotherName;

    /**
     * 父级 parent_id
     */
    @Column(name = "parent_id", nullable = false, length = 32)
    private String parentId;

    /**
     * 排序 menu_order
     */
    @Column(name = "menu_order", nullable = false)
    private Integer menuOrder;

    /**
     * 地址 path
     */
    @Column(name = "path", nullable = false, length = 64)
    private String path;

    /**
     * 组件 component
     */
    @Column(name = "component", nullable = false, length = 64)
    private String component;

    /**
     * 菜单描述 menu_description
     */
    @Column(name = "menu_description")
    private String menuDescription;

    /**
     * 状态 status
     */
    @Column(name = "status", nullable = false, length = 8)
    private String status;

    /**
     * 创建时间 create_time
     */
    @Column(name = "create_time", updatable = false)
    private Timestamp createTime;

    /**
     * 更新时间 update_time
     */
    @Column(name = "update_time", insertable = false)
    private Timestamp updateTime;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /** 子菜单 */
    @Transient
    private List<SysMenu> children = Lists.newArrayList();

    @Transient
    private String userId;

    @Transient
    private List<String> operIds;

    /**
     * Constructor
     */
    public SysMenu() {
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
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * @return the anotherName
     */
    public String getAnotherName() {
        return anotherName;
    }

    /**
     * @param anotherName the anotherName to set
     */
    public void setAnotherName(String anotherName) {
        this.anotherName = anotherName;
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
     * @return the menuOrder
     */
    public Integer getMenuOrder() {
        return menuOrder;
    }

    /**
     * @param menuOrder the menuOrder to set
     */
    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return the menuDescription
     */
    public String getMenuDescription() {
        return menuDescription;
    }

    /**
     * @param menuDescription the menuDescription to set
     */
    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the createTime
     */
    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the updateTime
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the children
     */
    public List<SysMenu> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the operIds
     */
    public List<String> getOperIds() {
        return operIds;
    }

    /**
     * @param operIds the operIds to set
     */
    public void setOperIds(List<String> operIds) {
        this.operIds = operIds;
    }

    /**
     * @return the menuTypeId
     */
    public String getMenuTypeId() {
        return menuTypeId;
    }

    /**
     * @param menuTypeId the menuTypeId to set
     */
    public void setMenuTypeId(String menuTypeId) {
        this.menuTypeId = menuTypeId;
    }

}
