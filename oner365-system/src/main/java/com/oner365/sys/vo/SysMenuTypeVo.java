package com.oner365.sys.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.oner365.data.commons.enums.StatusEnum;

/**
 * 菜单类型对象
 * 
 * @author zhaoyong
 */
public class SysMenuTypeVo implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private String id;

  /**
   * 类型名称
   */
  @NotBlank(message = "菜单类型名称不能为空")
  private String typeName;

  /**
   * 类型编码
   */
  @NotBlank(message = "菜单编码不能为空")
  private String typeCode;

  /**
   * 状态
   */
  @NotNull(message = "菜单状态不能为空")
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
   * Constructor
   */
  public SysMenuTypeVo() {
    super();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

}
