package com.oner365.neo4j.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

/**
 * 父节点
 * 
 * @author zhaoyong
 *
 */
@Node("ParentNode")
public class ParentNode implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Property(name = "name")
  private String name;

  @Relationship(type = "RelationEdge", direction = Relationship.Direction.OUTGOING)
  private Set<RelationNode> relation = new HashSet<>();
  
  public ParentNode() {
    super();
  }

  public ParentNode(String name) {
    this.name = name;
  }

  public void addRelation(SonNode sonNode, String name) {
    RelationNode relationNode = new RelationNode(name, sonNode);
    relation.add(relationNode);
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
   * @return the relation
   */
  public Set<RelationNode> getRelation() {
    return relation;
  }

  /**
   * @param relation the relation to set
   */
  public void setRelation(Set<RelationNode> relation) {
    this.relation = relation;
  }

}
