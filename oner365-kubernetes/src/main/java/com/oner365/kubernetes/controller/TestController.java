package com.oner365.kubernetes.controller;

import javax.annotation.Resource;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;

/**
 * Test Controller
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/kubernetes")
public class TestController extends BaseController {
  
  @Resource
  private DiscoveryClient discoveryClient;

  /**
   * test
   * 
   * @return ResponseData
   */
  @GetMapping("/test")
  public ResponseData<JSONObject> test(String data) {
    JSONObject result = new JSONObject();
    result.put("data", data);
    return ResponseData.success(result);
  }
}
