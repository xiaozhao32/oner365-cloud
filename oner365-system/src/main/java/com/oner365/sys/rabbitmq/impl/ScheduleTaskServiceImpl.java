package com.oner365.sys.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.api.enums.TaskStatusEnum;
import com.oner365.api.rabbitmq.IScheduleExecuteService;
import com.oner365.api.rabbitmq.IScheduleTaskService;
import com.oner365.api.rabbitmq.dto.InvokeParamDto;
import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.api.rabbitmq.dto.SysTaskLogDto;
import com.oner365.api.rabbitmq.dto.UpdateTaskExecuteSatusDto;
import com.oner365.common.ResponseData;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.sys.client.IMonitorServiceClient;
import com.oner365.sys.constants.SysConstants;
import com.oner365.util.DataUtils;

/**
 * IScheduleTaskService实现类
 * 
 * @author zhaoyong
 */
@Service
public class ScheduleTaskServiceImpl implements IScheduleTaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTaskServiceImpl.class);

  private static final String EXECUTE_STATUS = "executeStatus";

  @Autowired
  private IScheduleExecuteService scheduleExecuteService;

  @Autowired
  private IMonitorServiceClient monitorServiceClient;

  @Override
  public void scheduleTask(InvokeParamDto invokeParamDto) {
    LOGGER.info("MQ pull : {}", invokeParamDto);
    if (SysConstants.SCHEDULE_SERVER_NAME.equals(invokeParamDto.getTaskServerName())) {
      taskExecute(invokeParamDto.getConcurrent(), invokeParamDto.getTaskId(), invokeParamDto.getTaskParam());
    }
  }

  private void taskExecute(String concurrent, String taskId, JSONObject param) {
    LOGGER.info("taskExecute concurrent:{}, taskId:{}, param:{}", concurrent, taskId, param);
    if (PublicConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
      execute(taskId);
    } else {
      ResponseData<SysTaskDto> data = monitorServiceClient.getInfo(taskId);
      if (data.getCode() == ResultEnum.SUCCESS.getCode()) {
        JSONObject json = JSON.parseObject(data.getResult().toString());
        if (!StatusEnum.NO.name().equals(json.getString(EXECUTE_STATUS))) {
          execute(taskId);
        }
      }
    }
  }

  private void execute(String taskId) {
    long time = System.currentTimeMillis();
    UpdateTaskExecuteSatusDto updateTask = new UpdateTaskExecuteSatusDto();
    updateTask.setTaskId(taskId);
    updateTask.setExecuteStatus(StatusEnum.NO);
    scheduleExecuteService.updateTaskExecuteStatus(updateTask);
    LOGGER.info("taskExecute  update sysTask  executeStatus = 1");
    updateTask.setExecuteStatus(StatusEnum.YES);
    scheduleExecuteService.updateTaskExecuteStatus(updateTask);
    LOGGER.info("taskExecute  saveTaskLog ");
    SysTaskLogDto log = new SysTaskLogDto();
    log.setTaskId(taskId);
    log.setExecuteIp(DataUtils.getLocalhost());
    log.setExecuteServerName(SysConstants.SCHEDULE_SERVER_NAME);
    log.setStatus(TaskStatusEnum.NORMAL);
    log.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
    scheduleExecuteService.saveExecuteTaskLog(log);
  }

}
