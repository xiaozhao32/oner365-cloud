package com.oner365.files.service.impl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.files.dao.IFileStorageDao;
import com.oner365.files.dto.SysFileStorageDto;
import com.oner365.files.entity.SysFileStorage;
import com.oner365.files.service.IFileStorageService;
import com.oner365.files.vo.SysFileStorageVo;

/**
 * IFileStorageService实现类
 * 
 * @author zhaoyong
 */
@Service
public class FileStorageServiceImpl implements IFileStorageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);

  private static final String CACHE_NAME = "FileStorage";

  @Autowired
  private IFileStorageDao dao;

  @Override
  @GeneratorCache(CACHE_NAME)
  public PageInfo<SysFileStorageDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysFileStorage> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysFileStorageDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<SysFileStorageDto> findList(QueryCriteriaBean data) {
    try {
      if (data.getOrder() == null) {
        return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysFileStorageDto.class);
      }
      List<SysFileStorage> list = dao.findAll(QueryUtils.buildCriteria(data), 
          Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
      return convert(list, SysFileStorageDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysFileStorageDto getById(String id) {
    try {
      Optional<SysFileStorage> optional = dao.findById(id);
      return convert(optional.orElse(null), SysFileStorageDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysFileStorageDto save(SysFileStorageVo vo) {
    vo.setCreateTime(new Timestamp(System.currentTimeMillis()));
    SysFileStorage entity = dao.save(convert(vo, SysFileStorage.class));
    return convert(entity, SysFileStorageDto.class);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public int deleteById(String id) {
    try {
      dao.deleteById(id);
      return ResultEnum.SUCCESS.getCode();
    } catch (Exception e) {
      LOGGER.error("Error deleteById: ", e);
    }
    return ResultEnum.ERROR.getCode();
  }

}
