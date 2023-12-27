package com.oner365.zookeeper.service;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

/**
 * Zookeeper Service
 * 
 * @author zhaoyong
 * 
 */
public interface IZookeeperService {

  /**
   * 实例集合
   * 
   * @return List<ServiceInstance>
   */
  List<ServiceInstance> instanceList();
}
