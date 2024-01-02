package com.oner365.gateway.service;

import com.oner365.data.jpa.service.BaseService;
import com.oner365.gateway.vo.SysLogVo;

/**
 * 系统日志
 *
 * @author zhaoyong
 *
 */
public interface SysLogService extends BaseService {

  /**
   * 保存日志
   *
   * @param sysLog 对象
   */
  void save(SysLogVo sysLog);
}
