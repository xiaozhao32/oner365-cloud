package com.oner365.swagger.dto;

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
  
  private String id;
  private String name;
  private String path;
  private String component;
  private Boolean parent;
  private Boolean hidden;
  private Boolean alwaysShow;
  private String redirect;
  private SysMenuIconDto meta;
  private Boolean expand = false;
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
