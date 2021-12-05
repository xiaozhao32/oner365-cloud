package com.oner365.monitor.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 定时任务配置
 * 
 * @author zhaoyong
 */
@Configuration
public class ScheduleConfig implements SchedulerFactoryBeanCustomizer {

    @Autowired
    private DataSource dataSource;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 可选，QuartzScheduler
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        schedulerFactoryBean.setAutoStartup(true);
    }
}
