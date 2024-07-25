package com.oner365.influx.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.web.controller.BaseController;
import com.oner365.influx.entity.Mem;
import com.oner365.influx.service.InfluxService;

/**
 * 测试 Influx
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/influx/test")
public class InfluxTestController extends BaseController {

  @Resource
  private InfluxService service;

  /**
   * 保存对象
   * 
   * @param vo 对象
   * @return 是否成功
   */
  @PostMapping("/save")
  public Boolean save(@RequestBody Mem vo) {
    return service.save(vo);
  }

  /**
   * 查询列表
   *
   * @param data 查询对象
   * @return List<Mem>
   */
  @GetMapping("/list")
  public List<Mem> findList(String data) {
    return service.findList(data);
  }
}
