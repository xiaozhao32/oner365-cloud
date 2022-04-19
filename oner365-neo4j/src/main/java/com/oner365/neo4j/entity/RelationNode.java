package com.oner365.neo4j.entity;

import java.io.Serializable;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * 关系
 * 
 * @author zhaoyong
 *
 */
@RelationshipProperties
public class RelationNode implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * 关系名称
   */
  private String name;

  /**
   * 子对象
   */
  @TargetNode
  private SonNode sonNode;

  /**
   * 构造方法
   */
  public RelationNode() {
    super();
  }
  
  /**
   * 构造方法
   */
  public RelationNode(String name, SonNode sonNode) {
    this.name = name;
    this.sonNode = sonNode;

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

  /**
   * @return the sonNode
   */
  public SonNode getSonNode() {
    return sonNode;
  }

  /**
   * @param sonNode the sonNode to set
   */
  public void setSonNode(SonNode sonNode) {
    this.sonNode = sonNode;
  }
}
