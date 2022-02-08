package com.oner365.monitor.rabbitmq.impl;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.api.rabbitmq.dto.SysTaskLogDto;
import com.oner365.api.rabbitmq.dto.UpdateTaskExecuteSatusDto;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.rabbitmq.IScheduleSendExecuteService;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;

/**
 * IScheduleExecuteService实现类
 *
 * @author zhaoyong
 */
@Service
public class ScheduleSendExecuteServiceImpl implements IScheduleSendExecuteService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleSendExecuteServiceImpl.class);

  @Autowired
  private ISysTaskLogService sysTaskLogService;

  @Autowired
  private ISysTaskService sysTaskService;

  @Override
  public void updateTaskExecuteStatus(UpdateTaskExecuteSatusDto updateTask) throws SchedulerException, TaskException {
    LOGGER.info("updateTaskExecuteStatus :{}", updateTask);
    SysTaskDto sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
    if (sysTask != null) {
      sysTask.setExecuteStatus(updateTask.getExecuteStatus());
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
    }
  }

  @Override
  public void saveExecuteTaskLog(SysTaskLogDto taskLog) {
    LOGGER.info("saveExecuteTaskLog :{}", taskLog);
    SysTaskDto sysTask = sysTaskService.selectTaskById(taskLog.getTaskId());
    SysTaskLogVo sysTaskLog = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(taskLog)), SysTaskLogVo.class);
    if (sysTask != null) {
      sysTaskLog.setTaskGroup(sysTask.getTaskGroup());
      sysTaskLog.setTaskName(sysTask.getTaskName());
      sysTaskLog.setInvokeTarget(sysTask.getInvokeTarget());
      sysTaskLogService.addTaskLog(sysTaskLog);
    }
  }

}
