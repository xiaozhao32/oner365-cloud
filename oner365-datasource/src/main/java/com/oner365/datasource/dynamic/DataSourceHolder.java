package com.oner365.datasource.dynamic;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源类型设置
 * 
 * @author zhaoyong
 */
public class DataSourceHolder {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHolder.class);

  private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

  protected static final Map<String, DataSource> DATA_SOURCE_MAP = new HashMap<>();

  /**
   * Generate constructor
   */
  private DataSourceHolder() {
  }

  public static void setDataSource(String dbType) {
    LOGGER.info("切换到 [{}] 数据源", dbType);
    HOLDER.set(dbType);
  }

  public static String getDataSource() {
    return HOLDER.get();
  }

  public static void clearDataSource() {
    HOLDER.remove();
  }

  public static void setDataSourceMap(String key, DataSource value) {
    DATA_SOURCE_MAP.put(key, value);
  }

  public static DataSource getDataSourceMap(String key) {
    LOGGER.info("获取 [{}] 数据源", key);
    return DATA_SOURCE_MAP.get(key);
  }

}
