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
import com.oner365.common.enums.StatusEnum;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.system.ISystemMenuClient;
import com.oner365.swagger.dto.SysMenuDto;
import com.oner365.swagger.dto.SysMenuInfoDto;
import com.oner365.swagger.dto.SysMenuTreeSelectDto;
import com.oner365.swagger.dto.TreeSelect;
import com.oner365.swagger.vo.SysMenuVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 系统菜单
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 菜单")
@RequestMapping("/system/menus")
public class SysMenuController extends BaseController {
  
  @Autowired
  private ISystemMenuClient client;
  
  /**
   * 菜单列表
   *
   * @param sysMenuVo 菜单对象
   * @return ResponseData<SysMenuDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<SysMenuDto> list(@RequestBody SysMenuVo sysMenuVo) {
    return client.list(sysMenuVo);
  }
  
  /**
   * 获取菜单
   *
   * @param id 编号
   * @return ResponseData<SysMenuInfoDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SysMenuInfoDto> get(@PathVariable String id) {
    return client.getById(id);
  }
  
  /**
   * 修改菜单状态
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
   * 获取菜单下拉树列表
   *
   * @param sysMenuVo 菜单对象
   * @return ResponseData<List<TreeSelect>>
   */
  @ApiOperation("4.获取树型列表")
  @ApiOperationSupport(order = 4)
  @PostMapping("/tree")
  public ResponseData<List<TreeSelect>> treeselect(@RequestBody SysMenuVo sysMenuVo) {
    return client.treeselect(sysMenuVo);
  }
  
  /**
   * 加载对应角色菜单列表树
   *
   * @param sysMenuVo 菜单对象
   * @param roleId    String
   * @return ResponseData<SysMenuTreeSelectDto>
   */
  @ApiOperation("5.获取权限")
  @ApiOperationSupport(order = 5)
  @PostMapping("/role/{roleId}")
  public ResponseData<SysMenuTreeSelectDto> roleMenuTreeselect(@RequestBody SysMenuVo sysMenuVo, @PathVariable("roleId") String roleId) {
    return client.roleMenuTreeselect(sysMenuVo, roleId);
  }
  
  /**
   * 保存菜单
   *
   * @param sysMenuVo 菜单对象
   * @return ResponseData<ResponseResult<SysMenuDto>>
   */
  @ApiOperation("6.保存")
  @ApiOperationSupport(order = 6)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SysMenuDto>> save(@RequestBody SysMenuVo sysMenuVo) {
    return client.save(sysMenuVo);
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
