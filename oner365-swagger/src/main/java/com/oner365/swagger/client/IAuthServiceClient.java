package com.oner365.swagger.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.ResponseData;

/**
 * 权限认证服务
 * @author zhaoyong
 */
@FeignClient(value = "oner365-system")
public interface IAuthServiceClient {

    /**
     * 登录
     * @param json 登录参数
     * @return ResponseData
     */
    @PostMapping(value="/auth/login")
    ResponseData<Map<String, Object>> login(@RequestBody JSONObject json);

}
