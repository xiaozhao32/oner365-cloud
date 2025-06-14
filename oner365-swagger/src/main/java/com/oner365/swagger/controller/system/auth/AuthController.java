package com.oner365.swagger.controller.system.auth;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.swagger.client.system.ISystemAuthClient;
import com.oner365.swagger.dto.CaptchaImageDto;
import com.oner365.swagger.dto.LoginUserDto;
import com.oner365.swagger.dto.SysMenuOperDto;
import com.oner365.swagger.dto.SysMenuTreeDto;
import com.oner365.swagger.vo.LoginUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 认证登录接口
 *
 * @author zhaoyong
 */
@RestController
@Api(tags = "用户认证")
@RequestMapping("/system/auth")
public class AuthController {

    @Resource
    private ISystemAuthClient client;

    /**
     * 系统登录
     * @param loginUserVo 登录对象
     * @return ResponseData
     */
    @ApiOperation("1.登录")
    @ApiOperationSupport(order = 1)
    @PostMapping("/login")
    public ResponseData<LoginUserDto> login(@RequestBody LoginUserVo loginUserVo) {
        return client.login(loginUserVo);
    }

    /**
     * 获取验证码
     * @return CaptchaImageDto
     */
    @ApiOperation("2.获取验证码")
    @ApiOperationSupport(order = 2)
    @GetMapping("/captcha")
    public ResponseData<CaptchaImageDto> captchaImage() {
        return client.captchaImage();
    }

    /**
     * 获取左侧菜单
     * @return ArrayList<SysMenuTreeDto>
     */
    @ApiOperation("3.获取菜单权限")
    @ApiOperationSupport(order = 3)
    @GetMapping("/menu")
    public ResponseData<ArrayList<SysMenuTreeDto>> findMenuByRoles() {
        return client.findMenuByRoles();
    }

    /**
     * 获取菜单对应权限
     * @param menuId 菜单id
     * @return ArrayList<SysMenuOperDto>
     */
    @ApiOperation("4.获取菜单操作权限")
    @ApiOperationSupport(order = 4)
    @GetMapping("/menu/operation/{menuId}")
    public ResponseData<ArrayList<SysMenuOperDto>> findMenuOperByRoles(@PathVariable String menuId) {
        return client.findMenuOperByRoles(menuId);
    }

    /**
     * 退出登录
     * @return String
     */
    @ApiOperation("5.退出登录")
    @ApiOperationSupport(order = 5)
    @PostMapping("/logout")
    public ResponseData<String> logout() {
        return client.logout();
    }

}
