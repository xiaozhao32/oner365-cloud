package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysMenuTypeDao;
import com.oner365.sys.dto.SysMenuTypeDto;
import com.oner365.sys.entity.SysMenuType;
import com.oner365.sys.service.ISysMenuTypeService;
import com.oner365.sys.vo.SysMenuTypeVo;
import com.oner365.util.DataUtils;

/**
 * SysMenuType Service
 *
 * @author zhaoyong
 */
@Service
public class SysMenuTypeServiceImpl implements ISysMenuTypeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuTypeServiceImpl.class);

  private static final String CACHE_NAME = "SysMenuType";
  private static final String CACHE_MENU_NAME = "SysMenu";

  @Autowired
  private ISysMenuTypeDao dao;

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<SysMenuTypeDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysMenuType> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysMenuTypeDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuTypeDto> findList(QueryCriteriaBean data) {
    try {
      return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysMenuTypeDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysMenuTypeDto getById(String id) {
    try {
      Optional<SysMenuType> optional = dao.findById(id);
      return convert(optional.orElse(null), SysMenuTypeDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public SysMenuTypeDto save(SysMenuTypeVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setStatus(StatusEnum.YES);
      vo.setCreateTime(LocalDateTime.now());
    } else {
      vo.setUpdateTime(LocalDateTime.now());
    }
    SysMenuType entity = dao.save(convert(vo, SysMenuType.class));
    return convert(entity, SysMenuTypeDto.class);
  }
  
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public int editStatus(String id, StatusEnum status) {
    Optional<SysMenuType> optional = dao.findById(id);
    if (optional.isPresent()) {
      SysMenuType entity = optional.get();
      entity.setStatus(status);
      dao.save(entity);
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

  @Override
  public long checkCode(String id, String code) {
    try {
      Criteria<SysMenuType> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(SysConstants.TYPE_CODE, DataUtils.trimToNull(code)));
      if (!DataUtils.isEmpty(id)) {
        criteria.add(Restrictions.ne(SysConstants.ID, id));
      }
      return dao.count(criteria);
    } catch (Exception e) {
      LOGGER.error("Error checkCode:", e);
    }
    return ExistsEnum.NO.getCode();
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public SysMenuTypeDto getMenuTypeByTypeCode(String typeCode) {
    Assert.notNull(typeCode, "typeCode is not empty.");
    Criteria<SysMenuType> criteria = new Criteria<>();
    criteria.add(Restrictions.eq(SysConstants.TYPE_CODE, typeCode));
    return convert(dao.findOne(criteria), SysMenuTypeDto.class);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public int deleteById(String id) {
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

}
