package com.oner365.gateway.service.impl;

import javax.annotation.Resource;

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

    @Resource
    private ISysLogDao dao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(SysLogVo vo) {
        dao.save(convert(vo, SysLog.class));
    }

}
