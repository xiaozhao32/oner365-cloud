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
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.system.ISystemUserClient;
import com.oner365.swagger.dto.SysUserDto;
import com.oner365.swagger.vo.ModifyPasswordVo;
import com.oner365.swagger.vo.ResetPasswordVo;
import com.oner365.swagger.vo.SysUserVo;
import com.oner365.swagger.vo.check.CheckUserNameVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户管理
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "系统管理 - 用户")
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

  @Autowired
  private ISystemUserClient client;
  
  /**
   * 用户列表
   *
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysUserDto>>
   */
  @ApiOperation("1.用户列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SysUserDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }
  
  /**
   * 获取信息
   *
   * @param id 编号
   * @return ResponseData<SysUserDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SysUserDto> get(@PathVariable String id) {
    return client.getById(id);
  }
  
  /**
   * 个人信息
   *
   * @return ResponseData<SysUserDto>
   */
  @ApiOperation("3.个人信息")
  @ApiOperationSupport(order = 3)
  @GetMapping("/profile")
  public ResponseData<SysUserDto> profile() {
    return client.profile();
  }
  
  /**
   * 上传图片
   *
   * @param file     文件
   * @return ResponseData<String>
   */
  @ApiOperation("4.上传头像")
  @ApiOperationSupport(order = 4)
  @PostMapping("/avatar")
  public ResponseData<String> avatar(@RequestParam("avatarfile") MultipartFile file) {
    return client.avatar(file);
  }
  
  /**
   * 更新个人信息
   *
   * @param sysUserVo 对象
   * @return ResponseData<SysUserDto>
   */
  @ApiOperation("5.更新个人信息")
  @ApiOperationSupport(order = 5)
  @PostMapping("/update/profile")
  public ResponseData<SysUserDto> updateUserProfile(@RequestBody SysUserVo sysUserVo) {
    return client.updateUserProfile(sysUserVo);
  }
  
  /**
   * 判断用户是否存在
   *
   * @param checkUserNameVo 查询参数
   * @return ResponseData<Boolean>
   */
  @ApiOperation("6.判断存在")
  @ApiOperationSupport(order = 6)
  @PostMapping("/check")
  public ResponseData<Boolean> checkUserName(@RequestBody CheckUserNameVo checkUserNameVo) {
    return client.checkUserName(checkUserNameVo);
  }
  
  /**
   * 重置密码
   *
   * @param resetPasswordVo 查询参数
   * @return ResponseData<Boolean>
   */
  @ApiOperation("7.重置密码")
  @ApiOperationSupport(order = 7)
  @PostMapping("/reset")
  public ResponseData<Boolean> resetPassword(@RequestBody ResetPasswordVo resetPasswordVo) {
    return client.resetPassword(resetPasswordVo);
  }
  
  /**
   * 修改密码
   *
   * @param modifyPasswordVo 请求参数
   * @return ResponseData<Boolean>
   */
  @ApiOperation("8.修改密码")
  @ApiOperationSupport(order = 8)
  @PostMapping("/update/password")
  public ResponseData<Boolean> editPassword(@RequestBody ModifyPasswordVo modifyPasswordVo) {
    return client.editPassword(modifyPasswordVo);
  }
  
  /**
   * 修改用户状态
   *
   * @param id     主键
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @ApiOperation("9.修改状态")
  @ApiOperationSupport(order = 9)
  @PostMapping("/status/{id}")
  public ResponseData<Boolean> editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return client.editStatus(id, status);
  }
  
  /**
   * 用户保存
   *
   * @param sysUserVo 用户对象
   * @return ResponseData<ResponseResult<SysUserDto>>
   */
  @ApiOperation("10.保存")
  @ApiOperationSupport(order = 10)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SysUserDto>> save(@RequestBody SysUserVo sysUserVo) {
    return client.save(sysUserVo);
  }
  
  /**
   * 删除用户
   *
   * @param ids 编号
   * @return ResponseData<Boolean>
   */
  @ApiOperation("11.删除")
  @ApiOperationSupport(order = 11)
  @DeleteMapping("/delete")
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
  
  /**
   * 导出Excel
   *
   * @param data 参数
   * @return ResponseEntity<byte[]>
   */
  @ApiOperation("12.导出")
  @ApiOperationSupport(order = 12)
  @PostMapping("/export")
  public ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data) {
    return client.export(data);
  }
}
