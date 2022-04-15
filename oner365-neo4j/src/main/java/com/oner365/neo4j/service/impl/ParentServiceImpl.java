package com.oner365.neo4j.service.impl;

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
      e.printStackTrace();
    }
    return false;
  }

}
