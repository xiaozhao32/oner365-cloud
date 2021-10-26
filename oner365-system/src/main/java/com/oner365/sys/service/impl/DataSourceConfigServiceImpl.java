package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.datasource.constants.DataSourceConstants;
import com.oner365.sys.dao.IDataSourceDao;
import com.oner365.sys.entity.DataSourceConfig;
import com.oner365.sys.service.IDataSourceConfigService;
import com.oner365.util.DataUtils;

/**
 * IDataSourceConfigService实现类
 * @author zhaoyong
 */
@Service
public class DataSourceConfigServiceImpl implements IDataSourceConfigService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfigServiceImpl.class);
    
    private static final String CACHE_NAME = "DataSourceConfig";
    
    @Autowired
    private IDataSourceDao dao;

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<DataSourceConfig> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return dao.findAll(QueryUtils.buildCriteria(data), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public DataSourceConfig getById(String id) {
        try {
            Optional<DataSourceConfig> optional = dao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;    
    }
    
    @Override
    @RedisCacheAble(value = CACHE_NAME, key = "#connectName")
    public DataSourceConfig getConnectName(String connectName) {
        return dao.getConnectName(connectName);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public DataSourceConfig save(DataSourceConfig entity) {
        if (DataUtils.isEmpty(entity.getId())) {
            entity.setCreateTime(LocalDateTime.now());
        }
        String driverName = null;
        String url = null;
        if (DataSourceConstants.DB_TYPE_MYSQL.equals(entity.getDbType())) {
            driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
            url = "jdbc:mysql://" + entity.getIp() + ":" + entity.getPort() + PublicConstants.DELIMITER + entity.getDbName();
        } else if (DataSourceConstants.DB_TYPE_ORACLE.equals(entity.getDbType())) {
            driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
            url = "jdbc:oracle:thin:@" + entity.getIp() + ":" + entity.getPort() + ":" + entity.getDbName();
        }
        entity.setDriverName(driverName);
        entity.setUrl(url);
        entity.setUpdateTime(LocalDateTime.now());
        return dao.save(entity);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteById(String id) {
        dao.deleteById(id);
        return PublicConstants.SUCCESS_CODE;
    }

}
