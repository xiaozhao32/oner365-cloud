package com.oner365.zookeeper.service;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.ACL;
import org.springframework.cloud.client.ServiceInstance;

import com.oner365.zookeeper.vo.PathNodeVo;

/**
 * Zookeeper Service
 * 
 * @author zhaoyong
 * 
 */
public interface IZookeeperService {

  /**
   * 实例集合
   * 
   * @return List<ServiceInstance>
   */
  List<ServiceInstance> instanceList();

  /**
   * 创建节点
   * 
   * @param vo         节点对象
   * @param createMode mode
   * @return 返回路径
   */
  String createNode(PathNodeVo vo, CreateMode createMode);

  /**
   * 获取节点数据
   * 
   * @param path 节点地址
   * @return
   */
  String getNode(String path);

  /**
   * 判断节点是否存在
   * 
   * @param path 节点地址
   * @return 是否存在
   */
  Boolean checkNode(String path);

  /**
   * 删除节点
   * 
   * @param path 节点地址
   * @return 是否成功
   */
  Boolean deleteNode(String path);

  /**
   * 更新节点
   * 
   * @param vo 节点对象
   * @return 节点地址
   */
  String updateNode(PathNodeVo vo);
  
  /**
   * 获取ACL
   * @param path 节点地址
   * @return 集合
   */
  List<ACL> getAcl(String path);
}
