package com.oner365.zookeeper.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

/**
 * Node Vo
 * 
 * @author zhaoyong
 */
public class PathNodeVo implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 路径
   */
  @NotBlank(message = "{zookeeper.vo.pathNode.path.message}")
  private String path;

  /**
   * 节点值
   */
  private String value;

  public PathNodeVo() {
    super();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
