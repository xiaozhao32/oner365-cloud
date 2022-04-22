package com.oner365.swagger.controller.system;

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
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.system.ISystemRoleClient;
import com.oner365.swagger.dto.SysRoleDto;
import com.oner365.swagger.vo.SysRoleVo;
import com.oner365.swagger.vo.check.CheckRoleNameVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 角色管理
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 角色")
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

  @Autowired
  private ISystemRoleClient client;

  /**
   * 列表
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysRoleDto>>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SysRoleDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }

  /**
   * 获取信息
   *
   * @param id 编号
   * @return ResponseData<SysRoleDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SysRoleDto> get(@PathVariable String id) {
    return client.getById(id);
  }
  
  /**
   * 修改状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Integer>
   */
  @ApiOperation("3.修改状态")
  @ApiOperationSupport(order = 3)
  @PostMapping("/status/{id}")
  public ResponseData<Integer> editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return client.editStatus(id, status);
  }
  
  /**
   * 判断是否存在
   *
   * @param checkRoleNameVo 查询参数
   * @return ResponseData<Long>
   */
  @ApiOperation("4.判断角色名称存在")
  @ApiOperationSupport(order = 4)
  @PostMapping("/check")
  public ResponseData<Long> checkRoleName(@RequestBody CheckRoleNameVo checkRoleNameVo) {
    return client.check(checkRoleNameVo);
  }
  
  /**
   * 角色权限保存
   *
   * @param sysRoleVo 参数
   * @return ResponseData<ResponseResult<SysRoleDto>>
   */
  @ApiOperation("5.保存")
  @ApiOperationSupport(order = 5)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SysRoleDto>> save(@RequestBody SysRoleVo sysRoleVo) {
    return client.save(sysRoleVo);
  }
  
  /**
   * 删除
   *
   * @param ids 编号
   * @return ResponseData<Integer>
   */
  @ApiOperation("6.删除")
  @ApiOperationSupport(order = 6)
  @DeleteMapping("/delete")
  public ResponseData<Integer> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
  
  /**
   * 导出Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("7.导出")
  @ApiOperationSupport(order = 7)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    return client.export(data);
  }

}
