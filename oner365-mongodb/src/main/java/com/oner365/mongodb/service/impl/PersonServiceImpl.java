package com.oner365.mongodb.service.impl;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.mongodb.entity.Person;
import com.oner365.mongodb.repository.PersonRepository;
import com.oner365.mongodb.service.IPersonService;

/**
 * Person Service impl
 * 
 * @author zhaoyong
 */
@Service
public class PersonServiceImpl implements IPersonService {
  
  private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
      
  @Resource
  private PersonRepository repository;

  @Override
  @Transactional
  public Person save(Person entity) {
    if (DataUtils.isEmpty(entity.getId())) {
      entity.setCreateTime(Instant.now());
    }
    entity.setUpdateTime(Instant.now());
    return repository.save(entity);
  }
  
  @Override
  public Person getById(String id) {
    Optional<Person> optional = repository.findById(id);
    return optional.orElse(null);
  }

  @Override
  public PageInfo<Person> page(QueryCriteriaBean data) {
    try {
      Page<Person> page = repository.findAll(QueryUtils.buildPageRequest(data));
      return convert(page, Person.class);
    } catch (Exception e) {
      logger.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Transactional
  public Boolean delete(String id) {
    try {
      repository.deleteById(id);
      return Boolean.TRUE;
    } catch (Exception e) {
      logger.error("Error delete: ", e);
    }
    return Boolean.FALSE;
  }
}
