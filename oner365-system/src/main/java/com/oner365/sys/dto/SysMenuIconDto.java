package com.oner365.sys.dto;

import java.io.Serializable;

/**
 * 菜单树对象
 * 
 * @author zhaoyong
 *
 */
public class SysMenuIconDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 菜单名称
   */
  private String title;

  /**
   * 菜单icon
   */
  private String icon;

  public SysMenuIconDto() {
    super();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }
}
