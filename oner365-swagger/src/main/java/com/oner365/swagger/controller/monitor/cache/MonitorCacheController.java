package com.oner365.swagger.controller.monitor.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.monitor.IMonitorCacheClient;
import com.oner365.swagger.dto.CacheInfoDto;
import com.oner365.swagger.dto.CacheJedisInfoDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统监控 - 缓存管理
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "缓存管理")
@RequestMapping("/monitor/cache")
public class MonitorCacheController extends BaseController {

  @Autowired
  private IMonitorCacheClient client;

  /**
   * 缓存信息
   * 
   * @return ResponseData<CacheInfoDto>
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public ResponseData<CacheInfoDto> index() {
    return client.index();
  }

  /**
   * 缓存列表
   * 
   * @return ResponseData<List<CacheJedisInfoDto>>
   */
  @ApiOperation("2.缓存列表")
  @ApiOperationSupport(order = 2)
  @GetMapping("/list")
  public ResponseData<List<CacheJedisInfoDto>> cacheList() {
    return client.list();
  }

  /**
   * 清理缓存
   * 
   * @param index db
   * @return ResponseData<String>
   */
  @ApiOperation("3.清除缓存")
  @ApiOperationSupport(order = 3)
  @GetMapping("/clean")
  public ResponseData<String> clean(int index) {
    return client.clean(index);
  }
}
