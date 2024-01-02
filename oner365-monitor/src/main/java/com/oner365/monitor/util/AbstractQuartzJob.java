package com.oner365.monitor.util;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.monitor.constants.ScheduleConstants;

/**
 * 抽象quartz调用
 *
 * @author zhaoyong
 */
public abstract class AbstractQuartzJob implements Job {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQuartzJob.class);

  /**
   * 线程本地变量
   */
  private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

  @Override
  public void execute(JobExecutionContext context) {
    Object object = context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES);
    SysTaskDto sysTask;
    if (object instanceof SysTaskDto) {
      sysTask = (SysTaskDto) object;
    } else {
      sysTask = JSON.toJavaObject(JSON.parseObject(object.toString()), SysTaskDto.class);
    }
    try {
      before(context, sysTask);
      doExecute(context, sysTask);
    } catch (Exception e) {
      LOGGER.error("任务执行异常  - ：", e);
    }
    after(context, sysTask);
  }

  /**
   * 执行前
   *
   * @param context 工作执行上下文对象
   * @param sysTask 系统计划任务
   */
  protected void before(JobExecutionContext context, SysTaskDto sysTask) {
    threadLocal.set(DateUtil.getDate());
  }

  /**
   * 执行后
   *
   * @param context 工作执行上下文对象
   * @param sysTask 系统计划任务
   */
  protected void after(JobExecutionContext context, SysTaskDto sysTask) {
    threadLocal.remove();
  }

  /**
   * 执行方法，由子类重载
   *
   * @param context 工作执行上下文对象
   * @param sysTask 系统计划任务
   */
  protected abstract void doExecute(JobExecutionContext context, SysTaskDto sysTask);
}
