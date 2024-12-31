package com.oner365.monitor.controller.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

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
import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.web.controller.BaseController;
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

  @Resource
  private DiscoveryClient discoveryClient;

  @Resource
  private WebClient webClient;

  /**
   * 基本信息
   * 
   * @return List<List<ServiceInstance>>
   */
  @GetMapping("/index")
  public List<List<ServiceInstance>> index() {
    // 获取服务名称
    return discoveryClient.getServices().stream()
        .map(serviceName -> discoveryClient.getInstances(serviceName))
        .collect(Collectors.toList());
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
