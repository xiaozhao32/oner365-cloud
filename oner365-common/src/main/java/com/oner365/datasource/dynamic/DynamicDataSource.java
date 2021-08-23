package com.oner365.datasource.dynamic;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.oner365.datasource.config.DataSourceConfig;

/**
 * 多数据源支持
 * @author zhaoyong
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 获取数据源类型
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceConfig.getDataSource();
    }
    
}
