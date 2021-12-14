package com.oner365.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.gateway.dao.ISysLogDao;
import com.oner365.gateway.entity.SysLog;
import com.oner365.gateway.service.SysLogService;
import com.oner365.gateway.vo.SysLogVo;

/**
 * 系统日志监听实现类
 * 
 * @author zhaoyong
 */
@Service
public class SysLogServiceImpl implements SysLogService {

  @Autowired
  private ISysLogDao dao;

  @Override
  @Transactional(rollbackFor = RuntimeException.class)
  public void save(SysLogVo vo) {
    SysLog entity = toPojo(vo);
    dao.save(entity);
  }

  /**
   * 转换对象
   * 
   * @return SysLog
   */
  private SysLog toPojo(SysLogVo vo) {
    SysLog result = new SysLog();
    result.setId(vo.getId());
    result.setCreateTime(vo.getCreateTime());
    result.setMethodName(vo.getMethodName());
    result.setOperationContext(vo.getOperationContext());
    result.setOperationIp(vo.getOperationIp());
    result.setOperationName(vo.getOperationName());
    result.setOperationPath(vo.getOperationPath());
    return result;
  }

}
