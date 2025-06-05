package com.oner365.hadoop.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.DbType;
import com.alibaba.druid.pool.DruidDataSource;
import com.oner365.hadoop.config.properties.HiveProperties;

/**
 * Hive Configuration
 *
 * @author zhaoyong
 */
@Configuration
@EnableConfigurationProperties({ HiveProperties.class })
public class HiveConfiguration {

    @Resource
    private HiveProperties properties;

    @Bean(name = "hiveDataSource")
    @Qualifier("hiveDataSource")
    DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setDbType(DbType.hive);
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }

    @Bean(name = "hiveDruidTemplate")
    JdbcTemplate hiveDruidTemplate(@Qualifier("hiveDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
