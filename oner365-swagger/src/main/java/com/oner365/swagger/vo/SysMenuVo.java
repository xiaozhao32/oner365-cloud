package com.oner365.swagger.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.oner365.data.commons.enums.StatusEnum;

/**
 * 菜单对象
 * 
 * @author zhaoyong
 */
public class SysMenuVo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键 id
   */
  private String id;

  /**
   * 菜单类别 menu_type_id
   */
  private String menuTypeId;

  /**
   * 菜单名称 menu_name
   */
  private String menuName;

  /**
   * 别称 another_name
   */
  private String anotherName;

  /**
   * 父级 parent_id
   */
  private String parentId;

  /**
   * 排序 menu_order
   */
  private Integer menuOrder;

  /**
   * 地址 path
   */
  private String path;

  /**
   * 组件 component
   */
  private String component;

  /**
   * 菜单描述 menu_description
   */
  private String menuDescription;

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
   * 图标
   */
  private String icon;

  private List<SysMenuVo> children = new ArrayList<>();
  private String userId;
  private List<String> operIds;

  /**
   * Constructor
   */
  public SysMenuVo() {
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
   * @return the updateTime
   */
  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  /**
   * @param updateTime the updateTime to set
   */
  public void setUpdateTime(LocalDateTime updateTime) {
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

  public List<SysMenuVo> getChildren() {
    return children;
  }

  public void setChildren(List<SysMenuVo> children) {
    this.children = children;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<String> getOperIds() {
    return operIds;
  }

  public void setOperIds(List<String> operIds) {
    this.operIds = operIds;
  }

}
