package com.oner365.gateway.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.gateway.constants.ResponseData;
import com.oner365.gateway.constants.ResponseResult;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.enums.ResultEnum;
import com.oner365.gateway.enums.StatusEnum;
import com.oner365.gateway.page.PageInfo;
import com.oner365.gateway.query.QueryCriteriaBean;
import com.oner365.gateway.service.DynamicRouteService;
import com.oner365.gateway.vo.GatewayRouteVo;

/**
 * 动态路由控制
 *
 * @author liutao
 *
 */
@RestController
@RequestMapping("/route")
public class DynamicRouteController {

  @Autowired
  private DynamicRouteService dynamicRouteService;

  /**
   * 路由列表
   *
   * @param data 查询参数
   * @return ResponseData
   */
  @PostMapping("/list")
  public ResponseData<PageInfo<GatewayRouteDto>> pageList(@RequestBody QueryCriteriaBean data) {
    PageInfo<GatewayRouteDto> page = dynamicRouteService.pageList(data);
    return ResponseData.success(page);
  }

  /**
   * 获取路由
   *
   * @param id 编号
   * @return ResponseData
   */
  @GetMapping("/get/{id}")
  public ResponseData<GatewayRouteDto> get(@PathVariable String id) {
    return ResponseData.success(dynamicRouteService.getById(id));
  }

  /**
   * 增加路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseData
   */
  @PostMapping("/add")
  public ResponseData<ResponseResult<String>> add(@RequestBody GatewayRouteVo gatewayRouteVo) {
    String msg = dynamicRouteService.save(gatewayRouteVo);
    if (msg != null) {
      return ResponseData.success(ResponseResult.success(msg));
    }
    return ResponseData.error(ResultEnum.ERROR.getName());
  }

  /**
   * 刷新路由配置
   *
   * @return ResponseData
   */
  @GetMapping("/refresh")
  public ResponseData<List<GatewayRouteDto>> refresh() {
    List<GatewayRouteDto> list = dynamicRouteService.refreshRoute();
    return ResponseData.success(list);
  }

  /**
   * 更新路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseData
   */
  @PostMapping("/update")
  public ResponseData<ResponseResult<String>> update(@RequestBody GatewayRouteVo gatewayRouteVo) {
    String msg = dynamicRouteService.update(gatewayRouteVo);
    if (msg != null) {
      return ResponseData.success(ResponseResult.success(msg));
    }
    return ResponseData.error(ResultEnum.ERROR.getName());

  }

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return ResponseData
   */
  @GetMapping("/status/{id}/{status}")
  public ResponseData<ResponseResult<String>> updateRouteStatus(@PathVariable String id, @PathVariable StatusEnum status) {
    String msg = dynamicRouteService.updateRouteStatus(id, status);
    if (msg != null) {
      return ResponseData.success(ResponseResult.success(msg));
    }
    return ResponseData.error(ResultEnum.ERROR.getName());
  }

  /**
   * 删除路由
   *
   * @param ids 编号
   * @return ResponseData
   */
  @DeleteMapping("/delete")
  public ResponseData<ResponseResult<String>> delete(@RequestBody String... ids) {
    Arrays.stream(ids).forEach(id -> dynamicRouteService.delete(id));
    return ResponseData.success(ResponseResult.success(ResultEnum.SUCCESS.getName()));
  }

}
