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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
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
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data)));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuTypeDto> findList(QueryCriteriaBean data) {
    try {
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data)));
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
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public SysMenuTypeDto save(SysMenuTypeVo vo) {
    SysMenuType entity = toPojo(vo);
    if (DataUtils.isEmpty(entity.getId())) {
      entity.setStatus(StatusEnum.YES.getCode());
      entity.setCreateTime(LocalDateTime.now());
    } else {
      entity.setUpdateTime(LocalDateTime.now());
    }
    return convertDto(dao.save(entity));
  }
  
  /**
   * 转换对象
   * 
   * @return SysMenuType
   */
  public SysMenuType toPojo(SysMenuTypeVo vo) {
      SysMenuType result = new SysMenuType();
      result.setId(vo.getId());
      result.setCreateTime(vo.getCreateTime());
      result.setStatus(vo.getStatus());
      result.setTypeCode(vo.getTypeCode());
      result.setTypeName(vo.getTypeName());
      result.setUpdateTime(vo.getUpdateTime());
      return result;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public int editStatusById(String id, String status) {
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
  public SysMenuTypeDto getMenuTypeByTypeCode(String menuType) {
    return convertDto(dao.getMenuTypeByTypeCode(menuType));
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
