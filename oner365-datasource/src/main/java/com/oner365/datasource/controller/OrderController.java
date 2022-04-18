package com.oner365.datasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.datasource.dto.OrderDto;
import com.oner365.datasource.service.IOrderService;
import com.oner365.datasource.vo.OrderVo;

/**
 * 测试 jpa 方式的分库分表
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

  @Autowired
  private IOrderService service;

  /**
   * 订单列表
   *
   * @param data 查询参数
   * @return PageInfo<OrderDto>
   */
  @PostMapping("/list")
  public PageInfo<OrderDto> pageList(@RequestBody QueryCriteriaBean data) {
    return service.pageList(data);
  }

  /**
   * 获取订单
   *
   * @param id 编号
   * @return OrderDto
   */
  @GetMapping("/get/{id}")
  public OrderDto get(@PathVariable String id) {
    return service.getById(id);
  }

  /**
   * 修改订单状态
   *
   * @param id     主键
   * @param status 状态
   * @return Integer
   */
  @PostMapping("/status/{id}")
  public Integer editStatus(@PathVariable String id, @RequestParam("status") String status) {
    return service.editStatus(id, status);
  }

  /**
   * 用户订单保存
   *
   * @param vo 订单对象
   * @return ResponseResult<SysJobDto>
   */
  @PutMapping("/save")
  public ResponseResult<OrderDto> save(@RequestBody OrderVo vo) {
    if (vo != null) {
      OrderDto entity = service.save(vo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除用户订单
   *
   * @param ids 编号
   * @return Integer
   */
  @DeleteMapping("/delete")
  public Integer delete(@RequestBody String... ids) {
    int code = 0;
    for (String id : ids) {
      code = service.deleteById(id);
    }
    return code;
  }
}
