package com.oner365.monitor.mapper;

import java.util.List;

import com.oner365.monitor.entity.SysTaskLog;
import org.springframework.stereotype.Repository;

/**
 * 调度任务日志信息 数据层
 *
 * @author zhayong
 */
@Repository
public interface SysTaskLogMapper {
    /**
     * 获取quartz调度器日志的计划任务
     *
     * @param taskLog 调度日志信息
     * @return 调度任务日志集合
     */
    List<SysTaskLog> selectTaskLogList(SysTaskLog taskLog);

    /**
     * 查询所有调度任务日志
     *
     * @return 调度任务日志列表
     */
    List<SysTaskLog> selectTaskLogAll();

    /**
     * 通过调度任务日志ID查询调度信息
     *
     * @param id 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    SysTaskLog selectTaskLogById(Long id);

    /**
     * 新增任务日志
     *
     * @param taskLog 调度日志信息
     * @return 结果
     */
    int insertTaskLog(SysTaskLog taskLog);

    /**
     * 批量删除调度日志信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTaskLogByIds(Long[] ids);

    /**
     * 删除任务日志
     *
     * @param id 调度日志ID
     * @return 结果
     */
    int deleteTaskLogById(Long id);

    /**
     * 清空任务日志
     */
    void cleanTaskLog();
}
