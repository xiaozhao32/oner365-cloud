package com.oner365.neo4j.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oner365.neo4j.dao.SonRepository;
import com.oner365.neo4j.entity.SonNode;
import com.oner365.neo4j.service.SonService;

/**
 * 节点实现类
 * 
 * @author zhaoyong
 *
 */
@Service
public class SonServiceImpl implements SonService {
  
  private final Logger logger = LoggerFactory.getLogger(SonServiceImpl.class);

  @Resource
  private SonRepository repository;

  @Override
  public SonNode save(SonNode entity) {
    return repository.save(entity);
  }

  @Override
  public boolean delete(Long id) {
    try {
      repository.deleteById(id);
      return true;
    } catch (Exception e) {
      logger.error("son delete error:", e);
    }
    return false;
  }

}
