package com.oner365.datasource.config;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.oner365.common.datasource.constants.DataSourceConstants;
import com.oner365.common.datasource.util.DataSourceUtil;
import com.oner365.datasource.dynamic.DataSourceHolder;

/**
 * 数据源配置
 * 
 * @author zhaoyong
 *
 */
@Configuration
public class DataSourceConfig {
  
  private final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
  
  private final static String DS_TYPE = "ds_type";
  
  /**
   * 当前sharding主数据源
   */
  @Resource(name = "dataSource")
  private DataSource dataSource;
  
  /**
   * 获取数据源
   */
  @Bean(name = "primaryDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.druid")
  DataSource primaryDataSource() {
    return new DruidDataSource();
  }

  @Primary
  @Bean(name = "dynamicDataSource")
  DataSource dynamicDataSource() {
    DataSource ds = primaryDataSource();
    // 当前数据源
    DataSourceHolder.setDataSourceMap(DataSourceConstants.DataSourceType.PRIMARY.name(), ds);
    
    try {
      String sql = "select `connect_name`, `db_name`, `ip_address`, `url`, `user_name`, `password`, `port`, `driver_name`, `ds_type` from nt_data_source_config";
      List<Map<String, String>> list = DataSourceUtil.execute(ds.getConnection(), sql);
      list.forEach(map -> {
        if (DataSourceConstants.DS_TYPE_DB.equals(map.get(DS_TYPE))) {
          // 动态数据源
          DruidDataSource druidDatasource = builder(map);
          DataSourceHolder.setDataSourceMap(druidDatasource.getName(), druidDatasource);
        }
      });
    } catch (Exception e) {
      logger.error("dynamicDataSource error:", e);
    }
    
    return dataSource;
  }
  
  private static DruidDataSource builder(Map<String, String> map) {
    try (DruidDataSource datasource = new DruidDataSource()) {
      datasource.setUrl(map.get("url"));
      datasource.setUsername(map.get("user_name"));
      datasource.setPassword(map.get("password"));
      datasource.setDriverClassName(map.get("driver_name"));
      datasource.setDbType("com.alibaba.druid.pool.DruidDataSource");
      datasource.setName(map.get("connect_name"));
      return datasource;
    }
  }
}
