package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.oner365.sys.entity.DataSourceConfig;

/**
 * 数据源接口
 *
 * @author zhaoyong
 */
public interface IDataSourceConfigDao
        extends JpaRepository<DataSourceConfig, String>, JpaSpecificationExecutor<DataSourceConfig> {

    /**
     * 获取数据源
     * @param connectName 连接名称
     * @return DataSourceConfig
     */
    @Query(value = "from DataSourceConfig where connectName=?1 ")
    DataSourceConfig getConnectName(String connectName);

}
