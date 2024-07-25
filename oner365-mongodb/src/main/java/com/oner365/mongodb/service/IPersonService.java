package com.oner365.mongodb.service;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.mongodb.entity.Person;

/**
 * Person Service
 * 
 * @author zhaoyong
 */
public interface IPersonService extends BaseService {

  /**
   * Save
   * 
   * @param entity 实体对象
   * @return Person
   */
  Person save(Person entity);

  /**
   * Get
   * 
   * @param id 主键
   * @return Person
   */
  Person getById(String id);

  /**
   * Page List
   * 
   * @return PageInfo<Person>
   */
  PageInfo<Person> page(QueryCriteriaBean data);

  /**
   * Delete
   * 
   * @param id 主键
   * @return 是否成功
   */
  Boolean delete(String id);
}
