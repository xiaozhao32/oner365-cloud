package com.oner365.monitor.mapper;

import java.util.List;

import com.oner365.monitor.entity.SysTask;

/**
 * 调度任务信息 数据层
 *
 * @author zhaoyong
 */
public interface SysTaskMapper {
    /**
     * 查询调度任务日志集合
     *
     * @param task 调度信息
     * @return 操作日志集合
     */
    List<SysTask> selectTaskList(SysTask task);

    /**
     * 查询所有调度任务
     *
     * @return 调度任务列表
     */
    List<SysTask> selectTaskAll();

    /**
     * 通过调度ID查询调度任务信息
     *
     * @param taskId 调度ID
     * @return 角色对象信息
     */
    SysTask selectTaskById(Long taskId);

    /**
     * 通过调度ID删除调度任务信息
     *
     * @param taskId 调度ID
     * @return 结果
     */
    int deleteTaskById(Long taskId);

    /**
     * 批量删除调度任务信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteTaskByIds(Long[] ids);

    /**
     * 修改调度任务信息
     *
     * @param task 调度任务信息
     * @return 结果
     */
    int updateTask(SysTask task);

    /**
     * 新增调度任务信息
     *
     * @param task 调度任务信息
     * @return 结果
     */
    int insertTask(SysTask task);
}
