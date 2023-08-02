package com.oner365.neo4j.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oner365.neo4j.dao.ParentRepository;
import com.oner365.neo4j.entity.ParentNode;
import com.oner365.neo4j.service.ParentService;

/**
 * 节点实现类
 * 
 * @author zhaoyong
 *
 */
@Service
public class ParentServiceImpl implements ParentService {
  
  private final Logger logger = LoggerFactory.getLogger(ParentServiceImpl.class);

  @Autowired
  private ParentRepository repository;

  @Override
  public ParentNode save(ParentNode entity) {
    return repository.save(entity);
  }

  @Override
  public boolean delete(Long id) {
    try {
      repository.deleteById(id);
      return true;
    } catch (Exception e) {
      logger.error("parent delete error:", e);
    }
    return false;
  }

}
