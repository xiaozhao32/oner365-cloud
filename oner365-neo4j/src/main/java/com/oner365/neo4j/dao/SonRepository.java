package com.oner365.neo4j.dao;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.neo4j.entity.SonNode;

/**
 * 节点 Repository
 * 
 * @author zhaoyong
 *
 */
@Repository
@Transactional
public interface SonRepository extends Neo4jRepository<SonNode, Long> {

}
