package com.oner365.swagger.controller.gateway;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.client.gateway.IGatewayRouteClient;
import com.oner365.swagger.dto.GatewayRouteDto;
import com.oner365.swagger.vo.GatewayRouteVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 动态路由控制
 *
 * @author liutao
 *
 */
@RestController
@Api(tags = "动态路由控制")
@RequestMapping("/route")
public class DynamicRouteController {

  @Autowired
  private IGatewayRouteClient client;

  /**
   * 路由列表
   *
   * @param data 查询参数
   * @return ResponseData
   */
  @ApiOperation("1.列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<GatewayRouteDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }

  /**
   * 获取路由
   *
   * @param id 编号
   * @return ResponseData
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<GatewayRouteDto> get(@PathVariable String id) {
    return client.get(id);
  }

  /**
   * 增加路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseData
   */
  @ApiOperation("3.添加路由")
  @ApiOperationSupport(order = 3)
  @PostMapping("/add")
  public ResponseData<ResponseResult<String>> add(@RequestBody GatewayRouteVo gatewayRouteVo) {
    return client.add(gatewayRouteVo);
  }

  /**
   * 刷新路由配置
   *
   * @return ResponseData
   */
  @ApiOperation("4.刷新路由")
  @ApiOperationSupport(order = 4)
  @GetMapping("/refresh")
  public ResponseData<ArrayList<GatewayRouteDto>> refresh() {
    return client.refresh();
  }

  /**
   * 更新路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseData
   */
  @ApiOperation("5.更新路由")
  @ApiOperationSupport(order = 5)
  @PostMapping("/update")
  public ResponseData<ResponseResult<String>> update(@RequestBody GatewayRouteVo gatewayRouteVo) {
    return client.update(gatewayRouteVo);
  }

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return ResponseData
   */
  @ApiOperation("6.更新路由状态")
  @ApiOperationSupport(order = 6)
  @GetMapping("/status/{id}/{status}")
  public ResponseData<ResponseResult<String>> updateRouteStatus(@PathVariable String id, @PathVariable String status) {
    return client.updateRouteStatus(id, status);
  }

  /**
   * 删除路由
   *
   * @param ids 编号
   * @return ResponseData
   */
  @ApiOperation("7.删除路由")
  @ApiOperationSupport(order = 7)
  @DeleteMapping("/delete")
  public ResponseData<ResponseResult<String>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }

}
