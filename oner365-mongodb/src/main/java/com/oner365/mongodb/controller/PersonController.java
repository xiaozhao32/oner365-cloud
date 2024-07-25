package com.oner365.mongodb.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.mongodb.entity.Person;
import com.oner365.mongodb.service.IPersonService;

/**
 * Person Controller
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/person")
public class PersonController extends BaseController {

  @Resource
  private IPersonService service;

  /**
   * 分页查询
   * 
   * @param data 查询对象
   * @return PageInfo
   */
  @PostMapping("/page")
  public PageInfo<Person> page(@RequestBody QueryCriteriaBean data) {
    return service.page(data);
  }

  /**
   * Get Person
   * 
   * @param id 主键
   * @return Person
   */
  @GetMapping("/get/{id}")
  public Person get(@PathVariable("id") String id) {
    return service.getById(id);
  }

  /**
   * Save Person
   * 
   * @param entity 对象
   * @return Person
   */
  @PutMapping("/save")
  public Person save(@RequestBody Person entity) {
    return service.save(entity);
  }

  /**
   * Delete Person
   * 
   * @param id 主键
   * @return Person
   */
  @DeleteMapping("/delete/{id}")
  public Boolean delete(@PathVariable("id") String id) {
    return service.delete(id);
  }

}
