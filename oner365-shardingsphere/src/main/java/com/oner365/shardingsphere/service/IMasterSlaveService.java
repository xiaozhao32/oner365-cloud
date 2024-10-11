package com.oner365.shardingsphere.service;

import java.util.List;

import com.oner365.shardingsphere.entity.MasterSlave;

/**
 * 主从测试接口
 * 
 * @author zhaoyong
 */
public interface IMasterSlaveService {

  List<MasterSlave> findList();
  
  Boolean save(MasterSlave vo);
}
