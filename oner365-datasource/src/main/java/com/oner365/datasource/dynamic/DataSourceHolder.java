package com.oner365.datasource.dynamic;

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

}
