package com.oner365.shardingsphere.service;

import java.util.List;

import com.oner365.shardingsphere.entity.MasterSlave;
import com.oner365.shardingsphere.vo.MasterSlaveVo;

/**
 * 主从测试接口
 * 
 * @author zhaoyong
 */
public interface IMasterSlaveService {

  /**
   * 获取列表
   * @return 返回集合
   */
  List<MasterSlave> findList();
  
  /**
   * 保存
   * @param vo 主从对象
   * @return 是否成功
   */
  Boolean save(MasterSlaveVo vo);
}
