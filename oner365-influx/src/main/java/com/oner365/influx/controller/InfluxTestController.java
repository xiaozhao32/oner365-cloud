package com.oner365.influx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.controller.BaseController;
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

  @Autowired
  private InfluxService service;

  /**
   * 查询列表
   * @param query
   * @return List<Mem>
   */
  @GetMapping("/list")
  public List<Mem> findList(String data) {
    return service.findList(data);
  }
}
