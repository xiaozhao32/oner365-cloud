package com.oner365.neo4j.entity;

import java.io.Serializable;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * 子节点
 * 
 * @author zhaoyong
 *
 */
@Node
public class SonNode implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * 姓名
   */
  @Property(name = "name")
  private String name;
  
  /**
   * 构造方法
   */
  public SonNode() {
    super();
  }

  /**
   * 构造方法
   */
  public SonNode(String name) {
    this.name = name;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
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

}
