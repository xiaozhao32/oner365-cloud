package com.oner365.gateway.service;

import java.util.List;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisherAware;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.service.BaseService;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.vo.GatewayRouteVo;

/**
 * 动态路由接口
 * 
 * @author zhaoyong
 */
public interface DynamicRouteService extends BaseService, ApplicationEventPublisherAware {

  /**
   * 路由列表
   *
   * @return List
   */
  List<GatewayRouteDto> findList();

  /**
   * 分页查询路由列表
   * 
   * @param data 查询参数
   * @return PageInfo<GatewayRouteDto>
   */
  PageInfo<GatewayRouteDto> pageList(QueryCriteriaBean data);

  /**
   * 添加路由
   *
   * @param gatewayRoute 路由对象
   * @return Boolean
   */
  GatewayRouteDto save(GatewayRouteVo gatewayRoute);

  /**
   * 更新路由
   *
   * @param gatewayRoute 路由对象
   * @return Boolean
   */
  GatewayRouteDto update(GatewayRouteVo gatewayRoute);

  /**
   * 根据id查询路由
   *
   * @param id 编号
   * @return GatewayRouteDto
   */
  GatewayRouteDto getById(String id);

  /**
   * 删除路由
   *
   * @param id 编号
   * @return Boolean
   */
  Boolean delete(String id);

  /**
   * 路由
   *
   * @param gatewayRoute 路由对象
   * @return RouteDefinition
   */
  RouteDefinition assembleRouteDefinition(GatewayRouteDto gatewayRoute);

  /**
   * 刷新路由
   *
   * @return List
   */
  List<GatewayRouteDto> refreshRoute();

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return Boolean
   */
  Boolean editStatus(String id, StatusEnum status);

}
