package com.oner365.swagger.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Elasticsearch cluster mapping
 *
 * @author zhaoyong
 */
@ApiModel(value = "节点字段信息")
public class ClusterMappingDto implements Serializable {

  private static final long serialVersionUID = 1L;
  
  /** 字段名称 */
  @ApiModelProperty(value = "名称")
  private String name;
  /** 字段类型 */
  @ApiModelProperty(value = "属性")
  private String type;
  
  /**
   * 构造方法
   */
  public ClusterMappingDto() {
      super();
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }
  
}
