package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.datasource.constants.DataSourceConstants;
import com.oner365.sys.dao.IDataSourceConfigDao;
import com.oner365.sys.dto.DataSourceConfigDto;
import com.oner365.sys.entity.DataSourceConfig;
import com.oner365.sys.service.IDataSourceConfigService;
import com.oner365.sys.vo.DataSourceConfigVo;
import com.oner365.util.DataUtils;

/**
 * 数据源设置实现类
 *
 * @author zhaoyong
 */
@Service
public class DataSourceConfigServiceImpl implements IDataSourceConfigService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfigServiceImpl.class);

  private static final String CACHE_NAME = "DataSourceConfig";

  @Autowired
  private IDataSourceConfigDao dao;

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<DataSourceConfigDto> pageList(QueryCriteriaBean data) {
    try {
      Criteria<DataSourceConfig> criteria = QueryUtils.buildCriteria(data);
      return convertDto(dao.findAll(criteria, QueryUtils.buildPageRequest(data)));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public DataSourceConfigDto getById(String id) {
    try {
      Optional<DataSourceConfig> optional = dao.findById(id);
      if (optional.isPresent()) {
        return convertDto(optional.orElse(null));
      }
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = "#connectName")
  public DataSourceConfigDto getConnectName(String connectName) {
    Criteria<DataSourceConfig> criteria = new Criteria<>();
    criteria.add(Restrictions.eq("connectName", connectName));
    Optional<DataSourceConfig> optional = dao.findOne(criteria);
    if (optional.isPresent()) {
      return convertDto(optional.orElse(null));
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public DataSourceConfigDto save(DataSourceConfigVo vo) {
    DataSourceConfig entity = toPojo(vo);
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
    return convertDto(dao.save(entity));
  }

  /**
   * 转换对象
   * 
   * @return DataSourceConfig
   */
  private DataSourceConfig toPojo(DataSourceConfigVo vo) {
    DataSourceConfig result = new DataSourceConfig();
    result.setId(vo.getId());
    result.setConnectName(vo.getConnectName());
    result.setCreateTime(vo.getCreateTime());
    result.setDbName(vo.getDbName());
    result.setDbType(vo.getDbType());
    result.setDriverName(vo.getDriverName());
    result.setDsType(vo.getDsType());
    result.setIp(vo.getIp());
    result.setPassword(vo.getPassword());
    result.setPort(vo.getPort());
    result.setUpdateTime(vo.getUpdateTime());
    result.setUrl(vo.getUrl());
    result.setUserName(vo.getUserName());
    return result;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public int deleteById(String id) {
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

}
