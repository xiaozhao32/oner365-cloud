package com.oner365.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树
 * 
 * @author zhaoyong
 *
 */
public class SysMenuTreeDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private String id;

  /**
   * 菜单名称
   */
  private String name;

  /**
   * 路径
   */
  private String path;

  /**
   * 模块
   */
  private String component;

  /**
   * 是否父级
   */
  private Boolean parent;

  /**
   * 是否隐藏
   */
  private Boolean hidden;

  /**
   * 是否显示
   */
  private Boolean alwaysShow;

  /**
   * 重定向
   */
  private String redirect;

  /**
   * 图标对象
   */
  private SysMenuIconDto meta;

  /**
   * 是否扩展
   */
  private Boolean expand = false;

  /**
   * 子节点
   */
  private List<SysMenuTreeDto> children = new ArrayList<>();

  public SysMenuTreeDto() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }

  public Boolean getParent() {
    return parent;
  }

  public void setParent(Boolean parent) {
    this.parent = parent;
  }

  public Boolean getHidden() {
    return hidden;
  }

  public void setHidden(Boolean hidden) {
    this.hidden = hidden;
  }

  public Boolean getAlwaysShow() {
    return alwaysShow;
  }

  public void setAlwaysShow(Boolean alwaysShow) {
    this.alwaysShow = alwaysShow;
  }

  public String getRedirect() {
    return redirect;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
  }

  public SysMenuIconDto getMeta() {
    return meta;
  }

  public void setMeta(SysMenuIconDto meta) {
    this.meta = meta;
  }

  public Boolean getExpand() {
    return expand;
  }

  public void setExpand(Boolean expand) {
    this.expand = expand;
  }

  public List<SysMenuTreeDto> getChildren() {
    return children;
  }

  public void setChildren(List<SysMenuTreeDto> children) {
    this.children = children;
  }

}
