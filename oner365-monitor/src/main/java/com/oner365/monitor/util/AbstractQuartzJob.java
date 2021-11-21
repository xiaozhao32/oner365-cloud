package com.oner365.monitor.util;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.entity.SysTask;
import com.oner365.util.DateUtil;

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
        SysTask sysTask = new SysTask();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysTask);
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
    protected void before(JobExecutionContext context, SysTask sysTask) {
        threadLocal.set(DateUtil.getDate());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysTask 系统计划任务
     */
    protected void after(JobExecutionContext context, SysTask sysTask) {
        threadLocal.remove();
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysTask 系统计划任务
     */
    protected abstract void doExecute(JobExecutionContext context, SysTask sysTask);
}
