package com.oner365.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oner365.common.enums.StatusEnum;

/**
 * 字典类型对象
 * 
 * @author zhaoyong
 */
@Entity
@Table(name = "nt_sys_dict_item_type")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class SysDictItemType implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  @Id
  private String id;

  @Column(name = "dict_item_type_name", length = 32)
  private String typeName;

  @Column(name = "dict_type_code", nullable = false, length = 32)
  private String typeCode;

  @Column(name = "dict_item_type_des", length = 32)
  private String typeDes;

  @Column(name = "dict_item_type_order", length = 10)
  private Integer typeOrder;

  @Enumerated
  @Column(name = "status", nullable = false)
  private StatusEnum status;

  /**
   * Constructor
   */
  public SysDictItemType() {
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

  public String getTypeDes() {
    return typeDes;
  }

  public void setTypeDes(String typeDes) {
    this.typeDes = typeDes;
  }

  public Integer getTypeOrder() {
    return typeOrder;
  }

  public void setTypeOrder(Integer typeOrder) {
    this.typeOrder = typeOrder;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public String getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }
  
}
