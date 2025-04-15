package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单树
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "菜单树信息")
public class SysMenuTreeDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * 主键
   */
  @ApiModelProperty(value = "主键")
  private String id;
  
  /**
   * 菜单名称
   */
  @ApiModelProperty(value = "菜单名称")
  private String name;
  
  /**
   * 菜单路径
   */
  @ApiModelProperty(value = "菜单路径")
  private String path;
  
  /**
   * 菜单元素
   */
  @ApiModelProperty(value = "菜单元素")
  private String component;
  
  /**
   * 是否父节点
   */
  @ApiModelProperty(value = "是否父节点")
  private Boolean parent;
  
  /**
   * 是否隐藏
   */
  @ApiModelProperty(value = "是否隐藏")
  private Boolean hidden;
  
  /**
   * 是否显示
   */
  @ApiModelProperty(value = "是否显示")
  private Boolean alwaysShow;
  
  /**
   * 重定向地址
   */
  @ApiModelProperty(value = "重定向地址")
  private String redirect;
  
  /**
   * 菜单icon
   */
  @ApiModelProperty(value = "菜单icon")
  private SysMenuIconDto meta;
  
  /**
   * 是否展开
   */
  @ApiModelProperty(value = "是否展开")
  private Boolean expand = false;
  
  /**
   * 子菜单信息
   */
  @ApiModelProperty(value = "子菜单信息")
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
