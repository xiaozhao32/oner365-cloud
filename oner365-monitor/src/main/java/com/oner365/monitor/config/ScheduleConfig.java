package com.oner365.monitor.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.alibaba.druid.pool.DruidDataSource;
import com.oner365.data.datasource.constants.DataSourceConstants;

/**
 * 定时任务配置
 * 
 * @author zhaoyong
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulerFactoryBeanCustomizer {

    @Resource
    private DataSource dataSource;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        
        // Postgres 设置
        DruidDataSource source = (DruidDataSource) dataSource;
        if (DataSourceConstants.DRIVER_NAME_POSTGRESQL.equals(source.getDriverClassName())) {
          Properties properties = new Properties();
          properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
          schedulerFactoryBean.setQuartzProperties(properties);
        }

        // 可选，QuartzScheduler
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        // 设置自动启动，默认为true
        schedulerFactoryBean.setAutoStartup(true);
    }
}
