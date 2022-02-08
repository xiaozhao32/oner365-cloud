package com.oner365.monitor.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.api.rabbitmq.IScheduleTaskService;
import com.oner365.api.rabbitmq.dto.InvokeParamDto;
import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StatusEnum;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * IScheduleTaskService实现类
 *
 * @author zhaoyong
 */
@Service
public class ScheduleTaskServiceImpl implements IScheduleTaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleTaskServiceImpl.class);

  @Autowired
  private ISysTaskLogService sysTaskLogService;

  @Autowired
  private ISysTaskService sysTaskService;

  @Override
  public void scheduleTask(InvokeParamDto invokeParamDto) {
    LOGGER.info("MQ pull : {},MQ NAME:{}", JSON.toJSONString(invokeParamDto), PublicConstants.QUEUE_NAME);
    if (ScheduleConstants.SCHEDULE_SERVER_NAME.equals(invokeParamDto.getTaskServerName())) {
      taskExecute(invokeParamDto.getConcurrent(), invokeParamDto.getTaskId(), invokeParamDto.getTaskParam());
    }
  }

  private void taskExecute(String concurrent, String taskId, JSONObject param) {
    String status = StatusEnum.YES.getCode();
    SysTaskDto sysTask = sysTaskService.selectTaskById(taskId);
    if (sysTask != null) {
      if (PublicConstants.SCHEDULE_CONCURRENT.equals(concurrent)) {
        LOGGER.info("taskExecute  concurrent : {} , update sysTask  executeStatus = 0", concurrent);
        status = execute(taskId, param, sysTask);

      } else {
        if (!StatusEnum.NO.getCode().equals(sysTask.getExecuteStatus())) {
          status = execute(taskId, param, sysTask);
        }
        LOGGER.info("taskExecute  concurrent : {}", concurrent);
      }
      executeLog(sysTask, status);
    }
  }

  private String execute(String taskId, JSONObject param, SysTaskDto sysTask) {
    try {
      LOGGER.info("taskId:{}", taskId);
      sysTask.setExecuteStatus(StatusEnum.NO.getCode());
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
      int day = param.getInteger("day");
      String time = DateUtil.nextDay(day - 2 * day, DateUtil.FULL_TIME_FORMAT);
      String status = sysTaskLogService.deleteTaskLogByCreateTime(time);
      sysTask.setExecuteStatus(StatusEnum.YES.getCode());
      sysTaskService.save(convert(sysTask, SysTaskVo.class));
      return status;
    } catch (Exception e) {
      LOGGER.error("update sysTask Exception:", e);
      return StatusEnum.NO.getCode();
    }
  }

  private void executeLog(SysTaskDto sysTask, String status) {
    long time = System.currentTimeMillis();
    LOGGER.info("taskExecute  saveTaskLog ");
    SysTaskLogVo log = new SysTaskLogVo();
    log.setExecuteIp(DataUtils.getLocalhost());
    log.setExecuteServerName(ScheduleConstants.SCHEDULE_SERVER_NAME);
    log.setStatus(StatusEnum.YES.getCode());
    log.setTaskMessage("执行时间：" + (System.currentTimeMillis() - time) + "毫秒");
    log.setTaskGroup(sysTask.getTaskGroup());
    log.setTaskName(sysTask.getTaskName());
    log.setInvokeTarget(sysTask.getInvokeTarget());
    sysTaskLogService.addTaskLog(log);
  }

}
