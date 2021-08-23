package com.oner365.monitor.rabbitmq.impl;

import com.alibaba.fastjson.JSON;
import com.oner365.api.rabbitmq.dto.SysTaskLogDto;
import com.oner365.api.rabbitmq.dto.UpdateTaskExecuteSatusDto;
import com.oner365.monitor.entity.SysTask;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.rabbitmq.IScheduleSendExecuteService;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        SysTask sysTask = sysTaskService.selectTaskById(updateTask.getTaskId());
        if (sysTask != null) {
            sysTask.setExecuteStatus(updateTask.getExecuteStatus());
            sysTaskService.save(sysTask);
        }

    }

    @Override
    public void saveExecuteTaskLog(SysTaskLogDto taskLog) {
        LOGGER.info("saveExecuteTaskLog :{}", taskLog);
        SysTask sysTask = sysTaskService.selectTaskById(taskLog.getTaskId());
        SysTaskLog sysTaskLog = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(taskLog)), SysTaskLog.class);
        if (sysTask != null) {
            sysTaskLog.setTaskGroup(sysTask.getTaskGroup());
            sysTaskLog.setTaskName(sysTask.getTaskName());
            sysTaskLog.setInvokeTarget(sysTask.getInvokeTarget());
            sysTaskLogService.addTaskLog(sysTaskLog);
        }
    }


}
