package com.oner365.neo4j.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
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
      e.printStackTrace();
    }
    return false;
  }

}
