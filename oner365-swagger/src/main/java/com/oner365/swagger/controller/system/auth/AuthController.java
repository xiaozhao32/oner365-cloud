package com.oner365.swagger.controller.system.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.ResponseData;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.IAuthServiceClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户中心
 * 
 * @author zhaoyong
 */
@RefreshScope
@RestController
@RequestMapping("/system/auth")
@Api(value = "用户中心", tags = "用户中心接口")
public class AuthController extends BaseController {

    @Autowired
    private IAuthServiceClient client;

    @ApiOperation(value = "用户登录", notes = "用户中心-用户登录", httpMethod = "POST", response = ResponseData.class)
    @PostMapping("/login")
    public ResponseData<Map<String, Object>> login(
            @ApiParam(name = "userName", value = "账号", required = true) @RequestParam("userName") String userName,
            @ApiParam(name = "password", value = "密码", required = true) @RequestParam("password") String password) {
        JSONObject json = new JSONObject();
        json.put("userName", userName);
        json.put("password", password);
        return client.login(json);
    }
}
