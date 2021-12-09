package com.oner365.swagger.controller.system.auth;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.oner365.common.ResponseData;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.IAuthServiceClient;
import com.oner365.swagger.dto.LoginUserDto;
import com.oner365.swagger.vo.LoginUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 认证登录接口
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/system/auth")
@Api(tags = "用户认证")
public class AuthController extends BaseController {

  @Autowired
  private IAuthServiceClient client;

  /**
   * 系统登录
   *
   * @param loginUserVo 登录对象
   * @return ResponseData
   */
  @PostMapping("/login")
  @ApiOperation("登录")
  public ResponseData<LoginUserDto> login(@RequestBody LoginUserVo loginUserVo) {
    return client.login(loginUserVo);
  }

  /**
   * 退出登录
   * 
   * @return String
   */
  @PostMapping("/logout")
  @ApiOperation("退出登录")
  public ResponseData<String> logout() {
    return client.logout();
  }

  /**
   * 获取左侧菜单
   *
   * @param menuType 菜单类型
   * @return JSONArray
   */
  @GetMapping("/menu/{menuType}")
  @ApiOperation("获取菜单权限")
  public ResponseData<JSONArray> findMenuByRoles(@PathVariable String menuType) {
    return client.findMenuByRoles(menuType);
  }

  /**
   * 获取验证码
   * 
   * @return Map<String, Object>
   */
  @GetMapping("/captchaImage")
  @ApiOperation("获取验证码")
  public ResponseData<Map<String, Object>> captchaImage() {
    return client.captchaImage();
  }

  /**
   * 获取菜单对应权限
   *
   * @param menuId 菜单id
   * @return List<Map<String, Object>>
   */
  @GetMapping("/menu/operation/{menuId}")
  @ApiOperation("获取菜单操作权限")
  public ResponseData<List<Map<String, String>>> findMenuOperByRoles(@PathVariable String menuId) {
    return client.findMenuOperByRoles(menuId);
  }

}
