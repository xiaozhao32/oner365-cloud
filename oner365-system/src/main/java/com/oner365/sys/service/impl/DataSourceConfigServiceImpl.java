package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.GeneratorCache;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.datasource.constants.DataSourceConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
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
  @GeneratorCache(CACHE_NAME)
  public PageInfo<DataSourceConfigDto> pageList(QueryCriteriaBean data) {
    try {
      Page<DataSourceConfig> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, DataSourceConfigDto.class);
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
        return convert(optional.orElse(null), DataSourceConfigDto.class);
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
      return convert(optional.orElse(null), DataSourceConfigDto.class);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public DataSourceConfigDto save(DataSourceConfigVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setCreateTime(LocalDateTime.now());
    }
    String driverName = null;
    String url = null;
    if (DataSourceConstants.DB_TYPE_MYSQL.equals(vo.getDbType())) {
      driverName = DataSourceConstants.DRIVER_NAME_MYSQL;
      url = "jdbc:mysql://" + vo.getIp() + PublicConstants.COLON + vo.getPort() + PublicConstants.DELIMITER + vo.getDbName();
    } else if (DataSourceConstants.DB_TYPE_ORACLE.equals(vo.getDbType())) {
      driverName = DataSourceConstants.DRIVER_NAME_ORACLE;
      url = "jdbc:oracle:thin:@" + vo.getIp() + PublicConstants.COLON + vo.getPort() + PublicConstants.COLON + vo.getDbName();
    }
    vo.setDriverName(driverName);
    vo.setUrl(url);
    vo.setUpdateTime(LocalDateTime.now());
    DataSourceConfig entity = dao.save(convert(vo, DataSourceConfig.class));
    return convert(entity, DataSourceConfigDto.class);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Boolean deleteById(String id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

}
