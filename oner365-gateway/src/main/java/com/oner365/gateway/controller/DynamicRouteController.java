package com.oner365.gateway.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.gateway.constants.GatewayConstants;
import com.oner365.gateway.constants.ResponseData;
import com.oner365.gateway.dto.GatewayRouteDto;
import com.oner365.gateway.enums.ResultEnum;
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
  public ResponseData<PageInfo<GatewayRouteDto>> list(@RequestBody QueryCriteriaBean data) {
    PageInfo<GatewayRouteDto> page = dynamicRouteService.pageList(data);
    return new ResponseData<>(page);
  }

  /**
   * 获取路由
   *
   * @param id 编号
   * @return ResponseData
   */
  @GetMapping("/get/{id}")
  public ResponseData<GatewayRouteDto> get(@PathVariable String id) {
    return new ResponseData<>(dynamicRouteService.getById(id));
  }

  /**
   * 增加路由
   *
   * @param gatewayRouteVo 路由对象
   * @return ResponseData
   */
  @PostMapping("/add")
  public ResponseData<Map<String, Object>> add(@RequestBody GatewayRouteVo gatewayRouteVo) {
    Map<String, Object> result = new HashMap<>();
    result.put(GatewayConstants.CODE, ResultEnum.ERROR.getCode());
    String msg = dynamicRouteService.save(gatewayRouteVo);
    if (msg != null) {
      result.put(GatewayConstants.CODE, ResultEnum.SUCCESS.getCode());
      result.put(GatewayConstants.MSG, msg);
    }
    return new ResponseData<>(result);
  }

  /**
   * 刷新路由配置
   *
   * @return ResponseData
   */
  @GetMapping("/refresh")
  public ResponseData<List<GatewayRouteDto>> refresh() {
    return new ResponseData<>(dynamicRouteService.refreshRoute());
  }

  /**
   * 更新路由
   *
   * @param paramJson 路由对象
   * @return ResponseData
   */
  @PostMapping("/update")
  public ResponseData<Map<String, Object>> update(@RequestBody GatewayRouteVo gatewayRouteVo) {
    Map<String, Object> result = new HashMap<>();
    result.put(GatewayConstants.CODE, ResultEnum.ERROR.getCode());
    String msg = dynamicRouteService.update(gatewayRouteVo);
    if (msg != null) {
      result.put(GatewayConstants.CODE, ResultEnum.SUCCESS.getCode());
      result.put(GatewayConstants.MSG, msg);
    }
    return new ResponseData<>(result);

  }

  /**
   * 更新路由状态
   *
   * @param id     编号
   * @param status 状态
   * @return ResponseData
   */
  @GetMapping("/updateRouteStatus/{id}/{status}")
  public ResponseData<Map<String, Object>> updateRouteStatus(@PathVariable String id, @PathVariable String status) {
    Map<String, Object> result = new HashMap<>();
    result.put(GatewayConstants.CODE, ResultEnum.ERROR.getCode());
    String msg = dynamicRouteService.updateRouteStatus(id, status);
    if (msg != null) {
      result.put(GatewayConstants.CODE, ResultEnum.SUCCESS.getCode());
      result.put(GatewayConstants.MSG, msg);
    }
    return new ResponseData<>(result);
  }

  /**
   * 删除路由
   *
   * @param ids 编号
   * @return ResponseData
   */
  @DeleteMapping("/delete")
  public ResponseData<Map<String, Object>> delete(@RequestBody String... ids) {
    Arrays.stream(ids).forEach(id -> dynamicRouteService.delete(id));

    Map<String, Object> result = new HashMap<>();
    result.put(GatewayConstants.CODE, ResultEnum.SUCCESS.getCode());
    return new ResponseData<>(result);
  }

}
