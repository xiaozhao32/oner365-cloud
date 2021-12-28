package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 菜单详情对象
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "菜单详情")
public class SysMenuInfoDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  /**
   * 菜单对象
   */
  @ApiModelProperty(value = "菜单对象")
  private SysMenuDto sysMenu;
  
  /**
   * 菜单列表
   */
  @ApiModelProperty(value = "菜单列表")
  private List<String> menuOperList = new ArrayList<>();
  
  /**
   * 操作列表
   */
  @ApiModelProperty(value = "操作列表")
  private List<SysMenuOperationDto> operationList = new ArrayList<>();
  
  public SysMenuInfoDto() {
    super();
  }

  public SysMenuDto getSysMenu() {
    return sysMenu;
  }

  public void setSysMenu(SysMenuDto sysMenu) {
    this.sysMenu = sysMenu;
  }

  public List<String> getMenuOperList() {
    return menuOperList;
  }

  public void setMenuOperList(List<String> menuOperList) {
    this.menuOperList = menuOperList;
  }

  public List<SysMenuOperationDto> getOperationList() {
    return operationList;
  }

  public void setOperationList(List<SysMenuOperationDto> list) {
    this.operationList = list;
  }

}
