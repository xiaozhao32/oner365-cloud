package com.oner365.gateway.config;

import javax.annotation.Resource;

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

  @Resource
  private DynamicRouteService dynamicRouteService;

  @Override
  public void run(ApplicationArguments args) {
    dynamicRouteService.refreshRoute();
  }
}
