package com.oner365.neo4j.dao;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.oner365.neo4j.entity.ParentNode;

/**
 * 节点 Repository
 * 
 * @author zhaoyong
 *
 */
@Repository
public interface ParentRepository extends Neo4jRepository<ParentNode, Long> {

}
