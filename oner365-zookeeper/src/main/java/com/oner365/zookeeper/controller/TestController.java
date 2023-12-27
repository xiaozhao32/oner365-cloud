package com.oner365.zookeeper.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.zookeeper.service.IZookeeperService;

/**
 * Zookeeper Controller
 * 
 * @author zhaoyong
 * 
 */
@RestController
@RequestMapping("/test")
public class TestController {

  @Resource
  private IZookeeperService zookeeperService;

  /**
   * 实例集合
   * 
   * @return List<ServiceInstance>
   */
  @GetMapping("/instance/list")
  public List<ServiceInstance> instanceList() {
    return zookeeperService.instanceList();
  }

}
