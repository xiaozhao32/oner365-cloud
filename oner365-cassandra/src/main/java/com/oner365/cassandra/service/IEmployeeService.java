package com.oner365.cassandra.service;

import java.util.List;

import com.oner365.cassandra.entity.Employee;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;

/**
 * Employee Service
 * 
 * @author zhaoyong
 *
 */
public interface IEmployeeService extends BaseService {

  /**
   * 分页列表
   * 
   * @param data 查询参数
   * @return 列表
   */
  PageInfo<Employee> pageList(QueryCriteriaBean data);

  /**
   * 查询列表
   * 
   * @param data 查询参数
   * @return 列表
   */
  List<Employee> findList(QueryCriteriaBean data);

  /**
   * 查询详情
   * 
   * @param id
   * @return Employee
   */
  Employee getById(Integer id);

  /**
   * 保存
   * 
   * @param employee 对象
   * @return Employee
   */
  Employee save(Employee employee);
  
  /**
   * 删除
   *
   * @param id 编号
   * @return Boolean
   */
  Boolean deleteById(Integer id);
}
