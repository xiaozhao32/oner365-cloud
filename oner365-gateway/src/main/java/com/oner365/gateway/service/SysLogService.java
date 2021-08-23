package com.oner365.gateway.service;

import com.oner365.gateway.entity.SysLog;

/**
 * 系统日志
 * @author zhaoyong
 *
 */
public interface SysLogService {

    /**
     * 保存日志
     * @param sysLog 对象
     */
    void save(SysLog sysLog);
}
