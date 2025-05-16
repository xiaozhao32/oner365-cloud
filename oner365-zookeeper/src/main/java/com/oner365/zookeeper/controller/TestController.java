package com.oner365.zookeeper.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.ACL;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.zookeeper.service.IZookeeperService;
import com.oner365.zookeeper.vo.PathNodeVo;

/**
 * Zookeeper Controller
 * 
 * @author zhaoyong
 * 
 */
@RestController
@RequestMapping("/test")
public class TestController {

  @Resource
  private IZookeeperService zookeeperService;

  private static final String MESSAGE_NODE_EXISTS = "节点 [%s] 已存在";
  private static final String MESSAGE_NODE_NOT_EXISTS = "节点 [%s] 不存在";

  /**
   * 实例集合
   * 
   * @return List<ServiceInstance>
   */
  @GetMapping("/instance/list")
  public ResponseData<List<ServiceInstance>> instanceList() {
    List<ServiceInstance> list = zookeeperService.instanceList();
    ResponseData<List<ServiceInstance>> result = new ResponseData<>();
    result.setResult(list);
    result.setCode(ResultEnum.SUCCESS.getCode());
    result.setMessage(ResultEnum.SUCCESS.getName());
    return result;
  }

  /**
   * 创建节点
   * 
   * @param vo 节点对象
   * @return 节点地址
   */
  @PutMapping("/path/create")
  public ResponseData<String> createNode(@Validated @RequestBody PathNodeVo vo) {
    Boolean check = zookeeperService.checkNode(vo.getPath());
    if (Boolean.TRUE.equals(check)) {
      return ResponseData.error(String.format(MESSAGE_NODE_EXISTS, vo.getPath()));
    }
    String result = zookeeperService.createNode(vo, CreateMode.PERSISTENT);
    return ResponseData.success(result);
  }

  /**
   * 更新节点
   * 
   * @param vo 节点对象
   * @return 节点地址
   */
  @PostMapping("/path/update")
  public ResponseData<String> updateNode(@Validated @RequestBody PathNodeVo vo) {
    Boolean check = zookeeperService.checkNode(vo.getPath());
    if (!Boolean.TRUE.equals(check)) {
      return ResponseData.error(String.format(MESSAGE_NODE_NOT_EXISTS, vo.getPath()));
    }
    String result = zookeeperService.updateNode(vo);
    return ResponseData.success(result);
  }

  /**
   * 获取节点数据
   * 
   * @param path 节点地址
   * @return 返回结果
   */
  @GetMapping("/path/get")
  public ResponseData<String> getNode(@RequestParam String path) {
    Boolean check = zookeeperService.checkNode(path);
    if (!Boolean.TRUE.equals(check)) {
      return ResponseData.error(String.format(MESSAGE_NODE_NOT_EXISTS, path));
    }
    String result = zookeeperService.getNode(path);
    return ResponseData.success(result);
  }

  /**
   * 判断是否存在
   * 
   * @param path 节点地址
   * @return 是否存在
   */
  @GetMapping("/path/check")
  public ResponseData<Boolean> checkNode(@RequestParam String path) {
    Boolean result = zookeeperService.checkNode(path);
    return ResponseData.success(result);
  }

  /**
   * 删除节点
   * 
   * @param path 节点地址
   * @return 是否成功
   */
  @DeleteMapping("/path/delete")
  public ResponseData<Boolean> deleteNode(@RequestParam String path) {
    Boolean check = zookeeperService.checkNode(path);
    if (!Boolean.TRUE.equals(check)) {
      return ResponseData.error(String.format(MESSAGE_NODE_NOT_EXISTS, path));
    }
    Boolean result = zookeeperService.deleteNode(path);
    return ResponseData.success(result);
  }

  /**
   * 获取ACL
   * 
   * @param path 节点地址
   * @return 集合
   */
  @GetMapping("/path/acl")
  public ResponseData<List<ACL>> getAcl(@RequestParam String path) {
    Boolean check = zookeeperService.checkNode(path);
    if (!Boolean.TRUE.equals(check)) {
      ResponseData<List<ACL>> result = new ResponseData<>();
      result.setCode(ResultEnum.ERROR.getCode());
      result.setMessage(String.format(MESSAGE_NODE_NOT_EXISTS, path));
      return result;
    }
    List<ACL> list = zookeeperService.getAcl(path);
    ResponseData<List<ACL>> result = new ResponseData<>();
    result.setResult(list);
    result.setCode(ResultEnum.SUCCESS.getCode());
    result.setMessage(ResultEnum.SUCCESS.getName());
    return result;
  }

}
