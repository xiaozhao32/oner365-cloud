package com.oner365.shardingsphere.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.shardingsphere.dao.IMasterSlaveDao;
import com.oner365.shardingsphere.entity.MasterSlave;
import com.oner365.shardingsphere.service.IMasterSlaveService;
import com.oner365.shardingsphere.vo.MasterSlaveVo;

/**
 * 主从测试实现类
 * 
 * @author zhaoyong
 */
@Service
public class MasterSlaveServiceImpl implements IMasterSlaveService {
  
  private final Logger logger = LoggerFactory.getLogger(MasterSlaveServiceImpl.class);
  
  @Resource
  private IMasterSlaveDao dao;

  @Override
  public List<MasterSlave> findList() {
    return dao.findAll();
  }

  @Override
  @Transactional
  public Boolean save(MasterSlaveVo vo) {
    try {
      MasterSlave entity = dao.save(build(vo));
      logger.info("entity:{}", JSON.toJSONString(entity));
      return Boolean.TRUE;
    } catch (Exception e) {
      logger.error("MasterSlave save error", e);
    }
    return Boolean.FALSE;
  }
  
  private MasterSlave build(MasterSlaveVo vo) {
    MasterSlave result = new MasterSlave();
    result.setId(vo.getId());
    result.setName(vo.getName());
    return result;
  }

}
