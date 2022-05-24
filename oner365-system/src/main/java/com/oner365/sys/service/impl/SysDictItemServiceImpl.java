package com.oner365.sys.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.GeneratorCache;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysDictItemDao;
import com.oner365.sys.dto.SysDictItemDto;
import com.oner365.sys.entity.SysDictItem;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.vo.SysDictItemVo;
import com.oner365.util.DataUtils;

/**
 * 字典接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysDictItemServiceImpl implements ISysDictItemService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysDictItemServiceImpl.class);

  private static final String CACHE_NAME = "SysDictItem";
  private static final String CACHE_TYPE_NAME = "SysDictItemType";

  @Autowired
  private ISysDictItemDao dao;

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_TYPE_NAME, allEntries = true) })
  public SysDictItemDto save(SysDictItemVo vo) {
    SysDictItem entity = dao.save(convert(vo, SysDictItem.class));
    return convert(entity, SysDictItemDto.class);
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysDictItemDto getById(String id) {
    try {
      Optional<SysDictItem> optional = dao.findById(id);
      return convert(optional.orElse(null), SysDictItemDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public PageInfo<SysDictItemDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysDictItem> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysDictItemDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<SysDictItemDto> findList(QueryCriteriaBean data) {
    try {
      if (data.getOrder() == null) {
        return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysDictItemDto.class);
      }
      List<SysDictItem> list = dao.findAll(QueryUtils.buildCriteria(data), 
          Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
      return convert(list, SysDictItemDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  public Boolean checkCode(String id, String typeId, String code) {
    try {
      Criteria<SysDictItem> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(SysConstants.ITEM_CODE, DataUtils.trimToNull(code)));
      criteria.add(Restrictions.eq(SysConstants.TYPE_ID, DataUtils.trimToNull(typeId)));
      if (!DataUtils.isEmpty(id)) {
        criteria.add(Restrictions.ne(SysConstants.ID, id));
      }
      if (dao.count(criteria) > 0) {
        return Boolean.TRUE;
      }
    } catch (Exception e) {
      LOGGER.error("Error checkCode:", e);
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_TYPE_NAME, allEntries = true) })
  public Boolean deleteById(String id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = {
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_TYPE_NAME, allEntries = true) })
  public Boolean editStatus(String id, StatusEnum status) {
    Optional<SysDictItem> optional = dao.findById(id);
    if (optional.isPresent()) {
      SysDictItem entity = optional.get();
      entity.setStatus(status);
      dao.save(entity);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

}
