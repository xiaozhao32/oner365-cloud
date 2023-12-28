package com.oner365.monitor.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.fastjson.JSONObject;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.monitor.deploy.DeployServer;

import reactor.core.publisher.Mono;

/**
 * 服务监控
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/service")
public class ServiceController extends BaseController {

  @Autowired
  private DiscoveryClient discoveryClient;

  @Autowired
  private WebClient webClient;

  /**
   * 基本信息
   * 
   * @return List<List<ServiceInstance>>
   */
  @GetMapping("/index")
  public List<List<ServiceInstance>> index() {
    List<List<ServiceInstance>> serviceList = new ArrayList<>();
    // 获取服务名称
    List<String> serviceNameList = discoveryClient.getServices();
    for (String serviceName : serviceNameList) {
      // 获取服务中的实例列表
      List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
      serviceList.add(serviceInstances);
    }
    return serviceList;
  }

  /**
   * 获取信息
   * 
   * @param paramJson 属性
   * @return JSONObject
   */
  @PostMapping("/info")
  public JSONObject getActuatorEnv(@RequestBody JSONObject paramJson) {
    String uri = "/actuator/env";
    Mono<JSONObject> mono = webClient.get().uri(uri).retrieve().bodyToMono(JSONObject.class);
    return mono.block();
  }

  /**
   * 动态刷新配置
   * 
   * @return ResponseResult<String>
   */
  @GetMapping("/refresh")
  public ResponseResult<String> refreshConfig() {
    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

  /**
   * 上传服务
   *
   * @param multipartFile 文件
   * @return ResponseResult<String>
   */
  @PostMapping("/upload")
  public ResponseResult<String> uploadService(@RequestParam("multipartFile") MultipartFile multipartFile, String ip, int port,
      String serviceName) {
    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

  /**
   * 重启服务
   * 
   * @param deployServer 对象
   * @return ResponseResult<String>
   */
  @PostMapping("/reset")
  public ResponseResult<String> resetService(@RequestBody DeployServer deployServer) {
    return ResponseResult.success(ResultEnum.SUCCESS.getName());
  }

}
