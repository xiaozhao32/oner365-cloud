package com.oner365.monitor.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.oner365.api.rabbitmq.dto.SysTaskDto;
import com.oner365.monitor.constants.ScheduleConstants;
import com.oner365.monitor.exception.TaskException;
import com.oner365.monitor.exception.TaskException.Code;

/**
 * 定时任务工具类
 * 
 * @author zhaoyong
 *
 */
public class ScheduleUtils {
    
    private ScheduleUtils() {
        
    }
    
    /**
     * 得到quartz任务类
     *
     * @param sysTask 执行计划
     * @return 具体执行任务类
     */
    private static Class<? extends Job> getQuartzJobClass(SysTaskDto sysTask) {
        boolean isConcurrent = "0".equals(sysTask.getConcurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(String jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(String jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysTaskDto sysTask) throws SchedulerException, TaskException {
        Class<? extends Job> jobClass = getQuartzJobClass(sysTask);
        // 构建job信息
        String jobId = sysTask.getId();
        String jobGroup = sysTask.getTaskGroup();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysTask.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(sysTask, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
                .withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, sysTask);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(jobId, jobGroup));
        }

        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务
        if (sysTask.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
    }

    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysTaskDto job, CronScheduleBuilder cb)
            throws TaskException {
        switch (job.getMisfirePolicy()) {
        case ScheduleConstants.MISFIRE_DEFAULT:
            return cb;
        case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
            return cb.withMisfireHandlingInstructionIgnoreMisfires();
        case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
            return cb.withMisfireHandlingInstructionFireAndProceed();
        case ScheduleConstants.MISFIRE_DO_NOTHING:
            return cb.withMisfireHandlingInstructionDoNothing();
        default:
            throw new TaskException(
                    "The task misfire policy '" + job.getMisfirePolicy() + "' cannot be used in cron schedule tasks",
                    Code.CONFIG_ERROR);
        }
    }
}
