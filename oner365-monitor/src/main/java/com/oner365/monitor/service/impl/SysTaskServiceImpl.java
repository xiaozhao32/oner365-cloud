package com.oner365.monitor.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.oner365.api.enums.TaskStatusEnum;
import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.dao.ISysTaskDao;
import com.oner365.monitor.entity.SysTask;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.service.ISysTaskService;
import com.oner365.monitor.util.CronUtils;
import com.oner365.monitor.util.ScheduleUtils;
import com.oner365.monitor.vo.SysTaskVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * 定时任务调度信息 服务层
 *
 * @author liutao
 */
@Service
public class SysTaskServiceImpl implements ISysTaskService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysTaskServiceImpl.class);

  @Autowired
  private Scheduler scheduler;

  @Autowired
  private ISysTaskDao dao;

  /**
   * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
   */
  @PostConstruct
  public void init() throws SchedulerException {
    scheduler.clear();
    List<SysTask> taskList = dao.findAll();
    taskList.forEach(task -> {
      try {
        ScheduleUtils.createScheduleJob(scheduler, convert(task, SysTaskDto.class));
      } catch (Exception e) {
        LOGGER.error("init task error:", e);
      }
    });
  }

  @Override
  public PageInfo<SysTaskDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysTask> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysTaskDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  public SysTaskDto selectTaskById(String id) {
    Optional<SysTask> optional = dao.findById(id);
    return convert(optional.orElse(null), SysTaskDto.class);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean pauseTask(SysTaskVo vo) throws SchedulerException {
    Optional<SysTask> optional = dao.findById(vo.getId());
    if (optional.isPresent()) {
      SysTask sysTask = optional.get();
      sysTask.setStatus(TaskStatusEnum.PAUSE);
      dao.save(sysTask);
      scheduler.pauseJob(ScheduleUtils.getJobKey(sysTask.getId(), sysTask.getTaskGroup()));
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean resumeTask(SysTaskVo vo) throws SchedulerException {
    Optional<SysTask> optional = dao.findById(vo.getId());
    if (optional.isPresent()) {
      SysTask sysTask = optional.get();
      sysTask.setStatus(TaskStatusEnum.NORMAL);
      dao.save(sysTask);
      scheduler.resumeJob(ScheduleUtils.getJobKey(sysTask.getId(), sysTask.getTaskGroup()));
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean deleteTask(String id) throws SchedulerException {
    Optional<SysTask> optional = dao.findById(id);
    if (optional.isPresent()) {
      SysTask task = optional.get();
      String taskGroup = task.getTaskGroup();
      dao.deleteById(id);
      scheduler.deleteJob(ScheduleUtils.getJobKey(id, taskGroup));
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean deleteTaskByIds(String[] ids) throws SchedulerException {
    for (String id : ids) {
      deleteTask(id);
    }
    return Boolean.TRUE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean changeStatus(SysTaskVo task) throws SchedulerException {
    Boolean rows = Boolean.FALSE;
    if (TaskStatusEnum.NORMAL.equals(task.getStatus())) {
      rows = resumeTask(task);
    } else if (TaskStatusEnum.PAUSE.equals(task.getStatus())) {
      rows = pauseTask(task);
    }
    return rows;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean run(SysTaskVo task) throws SchedulerException {
    String taskGroup = task.getTaskGroup();
    Optional<SysTask> optional = dao.findById(task.getId());
    if (optional.isPresent()) {
      SysTask sysTask = optional.get();
      sysTask.setTaskGroup(taskGroup);
      JobDataMap dataMap = new JobDataMap();
      dataMap.put(ScheduleConstants.TASK_PROPERTIES, JSON.toJSONString(convert(sysTask, SysTaskDto.class)));
      scheduler.triggerJob(ScheduleUtils.getJobKey(task.getId(), taskGroup), dataMap);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean save(SysTaskVo task) throws SchedulerException, TaskException {
    boolean isAdd = DataUtils.isEmpty(task.getId());
    if (isAdd && DataUtils.isEmpty(task.getStatus())) {
      task.setStatus(TaskStatusEnum.PAUSE);
      task.setCreateTime(DateUtil.getDate());
    }
    SysTask entity = dao.save(convert(task, SysTask.class));
    if (isAdd) {
      ScheduleUtils.createScheduleJob(scheduler, convert(entity, SysTaskDto.class));
    }
    return Boolean.TRUE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean updateTask(SysTaskVo task) throws SchedulerException, TaskException {
    Optional<SysTask> optional = dao.findById(task.getId());
    if (optional.isPresent()) {
      SysTask sysTask = optional.get();
      save(task);
      updateSchedulerTask(task, sysTask.getTaskGroup());
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  /**
   * 更新任务
   *
   * @param task      任务对象
   * @param taskGroup 任务组名
   */
  public void updateSchedulerTask(SysTaskVo task, String taskGroup) throws SchedulerException, TaskException {
    String taskId = task.getId();
    // 判断是否存在
    JobKey taskKey = ScheduleUtils.getJobKey(taskId, taskGroup);
    if (scheduler.checkExists(taskKey)) {
      // 防止创建时存在数据问题 先移除，然后在执行创建操作
      scheduler.deleteJob(taskKey);
    }
    ScheduleUtils.createScheduleJob(scheduler, convert(task, SysTaskDto.class));
  }

  @Override
  public Boolean checkCronExpressionIsValid(String cronExpression) {
    return CronUtils.isValid(cronExpression);
  }
}
