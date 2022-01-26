package com.oner365.swagger.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oner365.swagger.client.system.ISystemMenuTypeClient;
import com.oner365.swagger.dto.SysMenuTypeDto;
import com.oner365.swagger.vo.SysMenuTypeVo;
import com.oner365.swagger.vo.check.CheckCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单类型管理
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "系统管理 - 菜单类型")
@RequestMapping("/system/menu/type")
public class SysMenuTypeController extends BaseController {

  @Autowired
  private ISystemMenuTypeClient client;

  /**
   * 列表
   *
   * @param data 参数
   * @return ResponseData<PageInfo<SysMenuTypeDto>>
   */
  @ApiOperation("1.获取分页列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SysMenuTypeDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }

  /**
   * 获取全部有效类型
   *
   * @return ResponseData<List<SysMenuTypeDto>>
   */
  @ApiOperation("2.获取全部有效类型")
  @ApiOperationSupport(order = 2)
  @GetMapping("/all")
  public ResponseData<List<SysMenuTypeDto>> findAll() {
    return client.all();
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return ResponseData<SysMenuTypeDto>
   */
  @ApiOperation("3.按id查询")
  @ApiOperationSupport(order = 3)
  @GetMapping("/get/{id}")
  public ResponseData<SysMenuTypeDto> get(@PathVariable String id) {
    return client.getById(id);
  }

  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Integer>
   */
  @ApiOperation("4.修改状态")
  @ApiOperationSupport(order = 4)
  @PostMapping("/status/{id}")
  public ResponseData<Integer> editStatus(@PathVariable String id, @RequestParam("status") String status) {
    return client.editStatus(id, status);
  }
  
  /**
   * 判断是否存在
   *
   * @param checkCodeVo 查询参数
   * @return ResponseData<Long>
   */
  @ApiOperation("5.判断是否存在")
  @ApiOperationSupport(order = 5)
  @PostMapping("/check")
  public ResponseData<Long> checkCode(@RequestBody CheckCodeVo checkCodeVo) {
    return client.checkCode(checkCodeVo);
  }
  
  /**
   * 保存
   *
   * @param sysMenuTypeVo 菜单类型对象
   * @return ResponseData<ResponseResult<SysMenuTypeDto>>
   */
  @ApiOperation("6.保存")
  @ApiOperationSupport(order = 6)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SysMenuTypeDto>> save(@RequestBody SysMenuTypeVo sysMenuTypeVo) {
    return client.save(sysMenuTypeVo);
  }
  
  /**
   * 删除
   *
   * @param ids 编号
   * @return ResponseData<List<Integer>>
   */
  @ApiOperation("7.删除")
  @ApiOperationSupport(order = 7)
  @DeleteMapping("/delete")
  public ResponseData<List<Integer>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }

}