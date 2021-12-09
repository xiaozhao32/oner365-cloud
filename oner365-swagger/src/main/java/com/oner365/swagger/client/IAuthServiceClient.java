package com.oner365.swagger.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONArray;
import com.oner365.common.ResponseData;
import com.oner365.swagger.dto.LoginUserDto;
import com.oner365.swagger.vo.LoginUserVo;

/**
 * 权限认证服务
 * 
 * @author zhaoyong
 */
@FeignClient(value = "oner365-system", contextId = "IAuthServiceClient")
public interface IAuthServiceClient {

  /**
   * 登录
   * 
   * @param json 登录参数
   * @return ResponseData
   */
  @PostMapping("/auth/login")
  ResponseData<LoginUserDto> login(@RequestBody LoginUserVo loginUserVo);

  /**
   * 登出
   * 
   * @return
   */
  @PostMapping("/auth/logout")
  ResponseData<String> logout();

  /**
   * 获取左侧菜单
   *
   * @param menuType 菜单类型
   * @return JSONArray
   */
  @GetMapping("/auth/menu/{menuType}")
  ResponseData<JSONArray> findMenuByRoles(@PathVariable(value = "menuType") String menuType);

  /**
   * 获取验证码
   * 
   * @return Map<String, Object>
   */
  @GetMapping("/auth/captchaImage")
  ResponseData<Map<String, Object>> captchaImage();

  /**
   * 获取菜单对应权限
   *
   * @param menuId 菜单id
   * @return List<Map<String, Object>>
   */
  @GetMapping("/auth/menu/operation/{menuId}")
  ResponseData<List<Map<String, String>>> findMenuOperByRoles(@PathVariable(value = "menuId") String menuId);

}
