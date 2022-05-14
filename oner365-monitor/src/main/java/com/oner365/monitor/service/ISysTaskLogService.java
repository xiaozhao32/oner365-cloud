package com.oner365.monitor.service;

import com.oner365.api.rabbitmq.dto.SysTaskLogDto;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.monitor.vo.SysTaskLogVo;

/**
 * 定时任务调度日志信息信息 服务层
 *
 * @author liutao
 */
public interface ISysTaskLogService extends BaseService {
  /**
   * 获取quartz调度器日志的计划任务
   *
   * @param data 查询参数
   * @return 调度任务日志集合
   */
  PageInfo<SysTaskLogDto> pageList(QueryCriteriaBean data);

  /**
   * 通过调度任务日志ID查询调度信息
   *
   * @param id 调度任务日志ID
   * @return 调度任务日志对象信息
   */
  SysTaskLogDto selectTaskLogById(String id);

  /**
   * 新增任务日志
   *
   * @param taskLog 调度日志信息
   */
  void addTaskLog(SysTaskLogVo taskLog);

  /**
   * 批量删除调度日志信息
   *
   * @param ids 需要删除的日志ID
   * @return 结果
   */
  int deleteTaskLogByIds(String[] ids);

  /**
   * 删除任务日志
   *
   * @param id 调度日志ID
   * @return 结果
   */
  int deleteTaskLogById(String id);

  /**
   * 清空任务日志
   */
  void cleanTaskLog();

  /**
   * 根据时间删除此时间之前的任务日志
   *
   * @param time 时间
   * @return 结果
   */
  StatusEnum deleteTaskLogByCreateTime(String time);
}
