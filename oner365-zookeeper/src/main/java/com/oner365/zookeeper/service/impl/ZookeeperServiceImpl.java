package com.oner365.zookeeper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import com.oner365.zookeeper.service.IZookeeperService;

/**
 * Zookeeper Service Impl
 * 
 * @author zhaoyong
 * 
 */
@Service
public class ZookeeperServiceImpl implements IZookeeperService {

  private final Logger logger = LoggerFactory.getLogger(ZookeeperServiceImpl.class);

  @Value("${spring.application.name}")
  private String name;

  @Resource
  private DiscoveryClient discoveryClient;

  @Override
  public List<ServiceInstance> instanceList() {
    List<ServiceInstance> list = discoveryClient.getInstances(name);
    logger.info("ServiceInstance List: {}", list.size());
    return list;
  }

}
