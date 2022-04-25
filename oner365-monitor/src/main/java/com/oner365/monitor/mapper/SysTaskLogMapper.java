package com.oner365.monitor.mapper;

import org.springframework.stereotype.Repository;

/**
 * 调度任务日志信息 数据层
 *
 * @author zhayong
 */
@Repository
public interface SysTaskLogMapper {

    /**
     * 清空任务日志
     */
    void cleanTaskLog();
    
}
