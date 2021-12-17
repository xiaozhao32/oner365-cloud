package com.oner365.swagger.client.system;

import java.util.ArrayList;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONArray;
import com.oner365.common.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.CaptchaImageDto;
import com.oner365.swagger.dto.LoginUserDto;
import com.oner365.swagger.dto.SysMenuOperDto;
import com.oner365.swagger.vo.LoginUserVo;

/**
 * 系统服务 - 权限认证
 * 
 * @author zhaoyong
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_AUTH_ID)
public interface ISystemAuthClient {

  /**
   * 登录
   * 
   * @param json 登录参数
   * @return ResponseData<LoginUserDto>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_AUTH_LOGIN)
  ResponseData<LoginUserDto> login(@RequestBody LoginUserVo loginUserVo);

  /**
   * 获取验证码
   * 
   * @return ResponseData<Map<String, Object>>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_AUTH_CAPTCHA_IMAGE)
  ResponseData<CaptchaImageDto> captchaImage();

  /**
   * 获取左侧菜单
   *
   * @param menuType 菜单类型
   * @return ResponseData<JSONArray>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_AUTH_MENU)
  ResponseData<JSONArray> findMenuByRoles(@PathVariable(value = "menuType") String menuType);

  /**
   * 获取菜单对应权限
   *
   * @param menuId 菜单id
   * @return ResponseData<ArrayList<SysMenuOperDto>>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_AUTH_MENU_OPERATION)
  ResponseData<ArrayList<SysMenuOperDto>> findMenuOperByRoles(@PathVariable(value = "menuId") String menuId);

  /**
   * 登出
   * 
   * @return ResponseData<String>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_AUTH_LOGOUT)
  ResponseData<String> logout();
  
}
