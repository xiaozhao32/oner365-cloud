package com.oner365.neo4j.service;

import com.oner365.neo4j.entity.SonNode;

/**
 * 节点接口
 * 
 * @author zhaoyong
 *
 */
public interface SonService {

  /**
   * 保存
   * 
   * @param entity 节点对象
   * @return SonNode
   */
  SonNode save(SonNode entity);

  /**
   * 删除
   * 
   * @param id 节点id
   */
  boolean delete(Long id);
}
