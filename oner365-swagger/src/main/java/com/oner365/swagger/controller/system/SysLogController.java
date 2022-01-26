package com.oner365.swagger.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.system.ISystemLogClient;
import com.oner365.swagger.dto.SysLogDto;
import com.oner365.swagger.vo.SysLogVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统日志控制器
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/log")
@Api(tags = "系统观察 - 日志")
public class SysLogController extends BaseController {
  
  @Autowired
  private ISystemLogClient client;
  
  /**
   * 列表
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysLogDto>>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SysLogDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }
  
  /**
   * 获取信息
   *
   * @param id 编号
   * @return ResponseData<SysLogDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SysLogDto> get(@PathVariable String id) {
    return client.getById(id);
  }
  
  /**
   * 保存
   *
   * @param sysLogVo 菜单类型对象
   * @return ResponseData<ResponseResult<String>>
   */
  @ApiOperation("3.保存")
  @ApiOperationSupport(order = 3)
  @PutMapping("/save")
  public ResponseData<ResponseResult<String>> save(@RequestBody SysLogVo sysLogVo) {
    return client.save(sysLogVo);
  }
  
  /**
   * 删除
   *
   * @param ids 编号
   * @return ResponseData<List<Integer>>
   */
  @ApiOperation("4.删除")
  @ApiOperationSupport(order = 4)
  @DeleteMapping("/delete")
  public ResponseData<List<Integer>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
  
  /**
   * 按日期删除日志
   *
   * @param days 天数
   * @return ResponseData<Integer>
   */
  @ApiOperation("5.删除日志")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/days/delete")
  public ResponseData<Integer> deleteLog(@RequestParam("days") Integer days) {
    return client.deleteDays(days);
  }
  
  /**
   * 导出日志
   *
   * @param data 查询参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("6.导出")
  @ApiOperationSupport(order = 6)
  @PostMapping("/export")
  public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
    return client.export(data);
  }

}
