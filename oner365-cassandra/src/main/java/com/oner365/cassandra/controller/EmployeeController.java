package com.oner365.cassandra.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.cassandra.entity.Employee;
import com.oner365.cassandra.service.IEmployeeService;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;

/**
 * Employee Controller
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController extends BaseController {

  @Resource
  private IEmployeeService service;

  /**
   * 获取分页列表
   * 
   * @param data 查询参数
   * @return 分页列表
   */
  @PostMapping("/page")
  public PageInfo<Employee> pageList(@RequestBody QueryCriteriaBean data) {
    return service.pageList(data);
  }

  /**
   * 获取列表
   * 
   * @param data 查询参数
   * @return 列表
   */
  @PostMapping("/list")
  public List<Employee> findList(@RequestBody QueryCriteriaBean data) {
    return service.findList(data);
  }

  /**
   * 获取详情
   *
   * @param id 编号
   * @return Employee
   */
  @GetMapping("/get/{id}")
  public ResponseResult<Employee> get(@PathVariable Integer id) {
    Employee entity = service.getById(id);
    if (entity != null) {
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.QUERY_ERROR.getName());
  }
  
  /**
   * 保存
   *
   * @param employeeVo 对象
   * @return ResponseResult<Employee>
   */
  @PutMapping("/save")
  public ResponseResult<Employee> save(@Validated @RequestBody Employee employeeVo) {
    if (employeeVo != null) {
      Employee entity = service.save(employeeVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }
  
  /**
   * 删除
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody Integer... ids) {
    return Arrays.stream(ids).map(id -> service.deleteById(id)).collect(Collectors.toList());
  }

}
