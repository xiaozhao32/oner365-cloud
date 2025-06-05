package com.oner365.monitor.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import com.oner365.api.rabbitmq.dto.SysTaskDto;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author zhaoyong
 *
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {

    @Override
    protected void doExecute(JobExecutionContext context, SysTaskDto sysTask) {
        JobInvokeUtil.invokeMethod(sysTask);
    }

}
