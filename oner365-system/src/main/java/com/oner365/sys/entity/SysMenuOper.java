package com.oner365.sys.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.data.commons.constants.PublicConstants;

/**
 * 菜单操作权限
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_menu_oper")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysMenuOper implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = PublicConstants.UUID)
  private String id;

  @Column(name = "menu_id", nullable = false, length = 32)
  private String menuId;

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = "operation_id")
  private SysMenuOperation sysMenuOperation;

  /**
   * Constructor
   */
  public SysMenuOper() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMenuId() {
    return menuId;
  }

  public void setMenuId(String menuId) {
    this.menuId = menuId;
  }

  public SysMenuOperation getSysMenuOperation() {
    return sysMenuOperation;
  }

  public void setSysMenuOperation(SysMenuOperation sysMenuOperation) {
    this.sysMenuOperation = sysMenuOperation;
  }

}
