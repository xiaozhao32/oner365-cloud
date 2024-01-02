package com.oner365.swagger.client.gateway;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.GatewayRouteDto;
import com.oner365.swagger.vo.GatewayRouteVo;

/**
 * 网关服务 - 路由管理
 *
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_GATEWAY, contextId = PathConstants.CONTEXT_GATEWAY_ROUTE_ID)
public interface IGatewayRouteClient {

  /**
   * 路由列表
   *
   * @param data 查询参数
   * @return ResponseData<String>
   */
  @PostMapping(PathConstants.REQUEST_GATEWAY_ROUTE_LIST)
  ResponseData<PageInfo<GatewayRouteDto>> list(@RequestBody QueryCriteriaBean data);

  /**
   * 获取路由
   *
   * @param id 编号
   * @return ResponseData
   */
  @GetMapping(PathConstants.REQUEST_GATEWAY_ROUTE_GET_ID)
  ResponseData<GatewayRouteDto> get(@PathVariable(value = "id") String id);

  /**
   * 增加路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseData
   */
  @PostMapping(PathConstants.REQUEST_GATEWAY_ROUTE_ADD)
  ResponseData<GatewayRouteDto> add(@RequestBody GatewayRouteVo gatewayRouteVo);

  /**
   * 刷新路由配置
   *
   * @return ResponseData
   */
  @GetMapping(PathConstants.REQUEST_GATEWAY_ROUTE_REFRESH)
  ResponseData<ArrayList<GatewayRouteDto>> refresh();

  /**
   * 更新路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseData
   */
  @PostMapping(PathConstants.REQUEST_GATEWAY_ROUTE_UPDATE)
  ResponseData<GatewayRouteDto> update(@RequestBody GatewayRouteVo gatewayRouteVo);

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return ResponseData
   */
  @GetMapping(PathConstants.REQUEST_GATEWAY_ROUTE_STATUS)
  ResponseData<Boolean> updateRouteStatus(@PathVariable(value = "id") String id,
      @PathVariable(value = "status") StatusEnum status);

  /**
   * 删除路由
   *
   * @param ids 编号
   * @return ResponseData
   */
  @DeleteMapping(PathConstants.REQUEST_GATEWAY_ROUTE_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);
}
