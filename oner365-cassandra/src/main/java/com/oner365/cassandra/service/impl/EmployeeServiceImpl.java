package com.oner365.cassandra.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.oner365.cassandra.dao.IEmployeeDao;
import com.oner365.cassandra.entity.Employee;
import com.oner365.cassandra.service.IEmployeeService;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;

/**
 * Employee Service
 * 
 * @author zhaoyong
 *
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

  @Resource
  private IEmployeeDao dao;

  public PageInfo<Employee> pageList(QueryCriteriaBean data) {
    PageRequest pageable = QueryUtils.buildPageRequest(data);
    Slice<Employee> slice = dao.findAll(pageable);
    return new PageInfo<>(slice.getContent(), slice.getPageable().getPageNumber() + 1,
        slice.getPageable().getPageSize(), slice.getContent().size());
  }

  @Override
  public List<Employee> findList(QueryCriteriaBean data) {
    PageRequest pageable = QueryUtils.buildPageRequest(data);
    Slice<Employee> slice = dao.findAll(pageable);
    return slice.getContent();
  }

  @Override
  public Employee getById(Integer id) {
    Optional<Employee> optional = dao.findById(id);
    return optional.orElse(null);
  }

  @Override
  public Employee save(Employee employee) {
    return dao.save(employee);
  }

  @Override
  public Boolean deleteById(Integer id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

}
