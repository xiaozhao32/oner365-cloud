package com.oner365.monitor.service.impl;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.monitor.dao.ISysTaskLogDao;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.mapper.SysTaskLogMapper;
import com.oner365.monitor.service.ISysTaskLogService;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author liutao
 */
@Service
public class SysTaskLogServiceImpl implements ISysTaskLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysTaskLogServiceImpl.class);

    @Autowired
    private ISysTaskLogDao dao;

    @Autowired
    private SysTaskLogMapper taskLogMapper;

    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param data 查询参数
     * @return 调度任务日志集合
     */
    @Override
    public Page<SysTaskLog> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return dao.findAll(QueryUtils.buildCriteria(data), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param id 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysTaskLog selectTaskLogById(String id) {
        Optional<SysTaskLog> optional = dao.findById(id);
        return optional.orElse(null);
    }

    /**
     * 新增任务日志
     *
     * @param taskLog 调度日志信息
     */
    @Override
    public void addTaskLog(SysTaskLog taskLog) {
        if(Strings.isNullOrEmpty(taskLog.getId())){
            taskLog.setCreateTime(new Date());
        }
        dao.save(taskLog);
    }

    /**
     * 批量删除调度日志信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTaskLogByIds(String[] ids) {
        for (String id : ids) {
            deleteTaskLogById(id);
        }
        return 1;
    }

    /**
     * 删除任务日志
     *
     * @param id 调度日志ID
     */
    @Override
    public int deleteTaskLogById(String id) {
        dao.deleteById(id);
        return 1;
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanTaskLog() {
        taskLogMapper.cleanTaskLog();
    }
    
    @Override
    public String deleteTaskLogByCreateTime(String time) {
        try{
            dao.deleteTaskLogByCreateTime(time);
            return PublicConstants.STATUS_YES;
        }catch(Exception e){
            LOGGER.error("Error deleteTaskLogByCreateTime: ", e);
            return PublicConstants.STATUS_NO;
        }
        
    }
}
