package com.oner365.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.oner365.gateway.service.DynamicRouteService;

/**
 * 加载动态路由信息
 * 
 * @author zhaoyong
 *
 */
@Component
public class GatewayRouteConfig implements ApplicationRunner {

  @Autowired
  private DynamicRouteService dynamicRouteService;

  @Override
  public void run(ApplicationArguments args) {
    dynamicRouteService.refreshRoute();
  }
}
