package com.oner365.monitor.util;

import org.quartz.JobExecutionContext;

import com.oner365.monitor.entity.SysTask;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author zhaoyong
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysTask sysTask) {
        JobInvokeUtil.invokeMethod(sysTask);
    }
}
