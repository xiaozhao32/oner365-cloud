package com.oner365.shardingsphere.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.web.controller.BaseController;
import com.oner365.shardingsphere.dto.OrderDto;
import com.oner365.shardingsphere.service.IOrderService;
import com.oner365.shardingsphere.vo.OrderVo;

/**
 * 测试 jpa 方式的分库分表
 * 
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

  @Resource
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
   * @return Boolean
   */
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam StatusEnum status) {
    return service.editStatus(id, status);
  }

  /**
   * 用户订单保存
   *
   * @param vo 订单对象
   * @return OrderDto
   */
  @PutMapping("/save")
  public OrderDto save(@RequestBody OrderVo vo) {
    return service.save(vo);
  }

  /**
   * 删除用户订单
   *
   * @param ids 编号
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> service.deleteById(id)).collect(Collectors.toList());
  }
}
