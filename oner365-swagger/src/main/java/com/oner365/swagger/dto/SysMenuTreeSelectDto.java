package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单树权限
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "菜单树权限")
public class SysMenuTreeSelectDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 菜单列表
   */
  @ApiModelProperty(value = "菜单列表")
  private List<TreeSelect> menus = new ArrayList<>();

  /**
   * 选中权限集合
   */
  @ApiModelProperty(value = "选中权限集合")
  private List<Integer> checkedKeys = new ArrayList<>();

  public SysMenuTreeSelectDto() {
    super();
  }

  public List<TreeSelect> getMenus() {
    return menus;
  }

  public void setMenus(List<TreeSelect> menus) {
    this.menus = menus;
  }

  public List<Integer> getCheckedKeys() {
    return checkedKeys;
  }

  public void setCheckedKeys(List<Integer> checkedKeys) {
    this.checkedKeys = checkedKeys;
  }

}
