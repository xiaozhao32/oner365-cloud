package com.oner365.files.service.impl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
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
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
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
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public Page<SysFileStorageDto> pageList(QueryCriteriaBean data) {
    try {
      Pageable pageable = QueryUtils.buildPageRequest(data);
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data), pageable));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysFileStorageDto> findList(QueryCriteriaBean data) {
    try {
      if (data.getOrder() == null) {
        return convertDto(dao.findAll(QueryUtils.buildCriteria(data)));
      }
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildSortRequest(data.getOrder())));
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
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysFileStorageDto save(SysFileStorageVo vo) {
    vo.setCreateTime(new Timestamp(System.currentTimeMillis()));
    SysFileStorage entity = toPojo(vo);
    return convertDto(dao.save(entity));
  }

  /**
   * PO对象
   * 
   * @return SysFileStorage
   */
  private SysFileStorage toPojo(SysFileStorageVo vo) {
    SysFileStorage result = new SysFileStorage();
    result.setCreateTime(vo.getCreateTime());
    result.setDirectory(vo.isDirectory());
    result.setDisplayName(vo.getDisplayName());
    result.setFastdfsUrl(vo.getFastdfsUrl());
    result.setFileName(vo.getFileName());
    result.setFilePath(vo.getFilePath());
    result.setFileStorage(vo.getFileStorage());
    result.setFileSuffix(vo.getFileSuffix());
    result.setId(vo.getId());
    result.setSize(vo.getSize());
    return result;
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
