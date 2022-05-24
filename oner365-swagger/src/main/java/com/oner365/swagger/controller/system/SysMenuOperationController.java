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
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.system.ISystemMenuOperationClient;
import com.oner365.swagger.dto.SysMenuOperationDto;
import com.oner365.swagger.vo.SysMenuOperationVo;
import com.oner365.swagger.vo.check.CheckCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单操作权限
 *
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "系统管理 - 菜单操作权限")
@RequestMapping("/system/menu/operation")
public class SysMenuOperationController extends BaseController {

  @Autowired
  private ISystemMenuOperationClient client;
  
  /**
   * 列表
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysMenuOperationDto>>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SysMenuOperationDto>> findList(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }
  
  /**
   * 获取信息
   *
   * @param id 编号
   * @return ResponseData<SysMenuOperationDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SysMenuOperationDto> getById(@PathVariable String id) {
    return client.getById(id);
  }
  
  /**
   * 判断是否存在
   *
   * @param checkCodeVo 查询参数
   * @return ResponseData<Boolean>
   */
  @ApiOperation("3.判断是否存在")
  @ApiOperationSupport(order = 3)
  @PostMapping("/check")
  public ResponseData<Boolean> checkCode(@RequestBody CheckCodeVo checkCodeVo) {
    return client.checkCode(checkCodeVo);
  }
  
  /**
   * 保存
   *
   * @param sysMenuOperationVo 操作对象
   * @return ResponseData<ResponseResult<SysMenuOperationDto>>
   */
  @ApiOperation("4.保存")
  @ApiOperationSupport(order = 4)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SysMenuOperationDto>> save(@RequestBody SysMenuOperationVo sysMenuOperationVo) {
    return client.save(sysMenuOperationVo);
  }
  
  /**
   * 删除
   *
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @ApiOperation("5.删除")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/delete")
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
}
