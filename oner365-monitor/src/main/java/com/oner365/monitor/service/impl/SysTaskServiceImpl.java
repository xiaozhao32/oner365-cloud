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
import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.common.enums.ResultEnum;
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

  /**
   * 获取quartz调度器的计划任务列表
   *
   * @param data 查询参数
   * @return Page<SysTaskDto>
   */
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

  /**
   * 通过调度任务ID查询调度信息
   *
   * @param id 调度任务ID
   * @return 调度任务对象信息
   */
  @Override
  public SysTaskDto selectTaskById(String id) {
    Optional<SysTask> optional = dao.findById(id);
    return convert(optional.orElse(null), SysTaskDto.class);
  }

  /**
   * 暂停任务
   *
   * @param task 调度信息
   * @throws TaskException 异常
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int pauseTask(SysTaskVo task) throws SchedulerException, TaskException {
    String taskId = task.getId();
    String taskGroup = task.getTaskGroup();
    task.setStatus(ScheduleConstants.Status.PAUSE.getValue());
    save(task);
    scheduler.pauseJob(ScheduleUtils.getJobKey(taskId, taskGroup));
    return ResultEnum.SUCCESS.getCode();
  }

  /**
   * 恢复任务
   *
   * @param task 调度信息
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int resumeTask(SysTaskVo task) throws SchedulerException, TaskException {
    String taskId = task.getId();
    String taskGroup = task.getTaskGroup();
    task.setStatus(ScheduleConstants.Status.NORMAL.getValue());
    save(task);
    scheduler.resumeJob(ScheduleUtils.getJobKey(taskId, taskGroup));
    return ResultEnum.SUCCESS.getCode();
  }

  /**
   * 删除任务后，所对应的trigger也将被删除
   *
   * @param task 调度信息
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int deleteTask(String id) throws SchedulerException {
    Optional<SysTask> optional = dao.findById(id);
    if (optional.isPresent()) {
      SysTask task = optional.get();
      String taskGroup = task.getTaskGroup();
      dao.deleteById(id);
      scheduler.deleteJob(ScheduleUtils.getJobKey(id, taskGroup));
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

  /**
   * 批量删除调度信息
   *
   * @param ids 需要删除的任务ID
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public void deleteTaskByIds(String[] ids) throws SchedulerException {
    for (String id : ids) {
      deleteTask(id);
    }
  }

  /**
   * 任务调度状态修改
   *
   * @param task 调度信息
   * @return int
   * @throws SchedulerException 异常
   * @throws TaskException      异常
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int changeStatus(SysTaskVo task) throws SchedulerException, TaskException {
    int rows = 0;
    if (ScheduleConstants.Status.NORMAL.getValue().equals(task.getStatus())) {
      rows = resumeTask(task);
    } else if (ScheduleConstants.Status.PAUSE.getValue().equals(task.getStatus())) {
      rows = pauseTask(task);
    }
    return rows;
  }

  /**
   * 立即运行任务
   *
   * @param task 调度信息
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public void run(SysTaskVo task) throws SchedulerException {
    String taskGroup = task.getTaskGroup();
    Optional<SysTask> optional = dao.findById(task.getId());
    if (optional.isPresent()) {
      SysTask sysTask = optional.get();
      sysTask.setTaskGroup(taskGroup);
      JobDataMap dataMap = new JobDataMap();
      dataMap.put(ScheduleConstants.TASK_PROPERTIES, JSON.toJSONString(convert(sysTask, SysTaskDto.class)));
      scheduler.triggerJob(ScheduleUtils.getJobKey(task.getId(), taskGroup), dataMap);
    }
  }

  /**
   * 保存任务
   *
   * @param task 调度信息 调度信息
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int save(SysTaskVo task) throws SchedulerException, TaskException {
    boolean isAdd = DataUtils.isEmpty(task.getId());
    if (isAdd && DataUtils.isEmpty(task.getStatus())) {
      task.setStatus(ScheduleConstants.Status.PAUSE.getValue());
      task.setCreateTime(DateUtil.getDate());
    }
    SysTask entity = dao.save(convert(task, SysTask.class));
    if (isAdd) {
      ScheduleUtils.createScheduleJob(scheduler, convert(entity, SysTaskDto.class));
    }
    return ResultEnum.SUCCESS.getCode();
  }

  /**
   * 更新任务的时间表达式
   *
   * @param task 调度信息
   */
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int updateTask(SysTaskVo task) throws SchedulerException, TaskException {
    Optional<SysTask> optional = dao.findById(task.getId());
    if (optional.isPresent()) {
      SysTask sysTask = optional.get();
      save(task);
      updateSchedulerTask(task, sysTask.getTaskGroup());
    }
    return ResultEnum.SUCCESS.getCode();
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

  /**
   * 校验cron表达式是否有效
   *
   * @param cronExpression 表达式
   * @return 结果
   */
  @Override
  public boolean checkCronExpressionIsValid(String cronExpression) {
    return CronUtils.isValid(cronExpression);
  }
}
