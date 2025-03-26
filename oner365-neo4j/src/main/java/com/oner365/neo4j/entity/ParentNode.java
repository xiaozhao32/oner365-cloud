package com.oner365.neo4j.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.stereotype.Component;

/**
 * 人员节点
 * 
 * @author zhaoyong
 *
 */
@Node
@Component
public class ParentNode implements Serializable {

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
   * 关系
   */
  @Relationship(direction = Relationship.Direction.OUTGOING)
  private List<RelationNode> relationList = new ArrayList<>();
  
  /**
   * 构造方法
   */
  public ParentNode() {
    super();
  }

  /**
   * 构造方法
   */
  public ParentNode(String name) {
    this.name = name;
  }

  public void addRelation(RelationNode relationNode) {
    relationList.add(relationNode);
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
   * @return the relationList
   */
  public List<RelationNode> getRelationList() {
    return relationList;
  }

  /**
   * @param relationList the relationList to set
   */
  public void setRelationList(List<RelationNode> relationList) {
    this.relationList = relationList;
  }

}
