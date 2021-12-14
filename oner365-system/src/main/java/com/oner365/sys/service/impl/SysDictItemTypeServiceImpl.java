package com.oner365.sys.service.impl;

import java.util.ArrayList;
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
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysDictItemTypeDao;
import com.oner365.sys.dto.SysDictItemDto;
import com.oner365.sys.dto.SysDictItemTypeDto;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.sys.vo.SysDictItemTypeVo;
import com.oner365.util.DataUtils;

/**
 * 字典类型接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysDictItemTypeServiceImpl implements ISysDictItemTypeService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysDictItemTypeServiceImpl.class);

  private static final String CACHE_NAME = "SysDictItemType";
  private static final String CACHE_ITEM_NAME = "SysDictItem";

  @Autowired
  private ISysDictItemTypeDao dao;

  @Autowired
  private ISysDictItemService sysDictItemService;

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true) })
  public SysDictItemTypeDto save(SysDictItemTypeVo vo) {
    SysDictItemType entity = toPojo(vo);
    if (DataUtils.isEmpty(entity.getId())) {
      entity.setId(entity.getTypeCode());
    }
    return convertDto(dao.save(entity));
  }

  /**
   * 转换对象
   * 
   * @return SysDictItemType
   */
  private SysDictItemType toPojo(SysDictItemTypeVo vo) {
    SysDictItemType result = new SysDictItemType();
    result.setId(vo.getId());
    result.setStatus(vo.getStatus());
    result.setTypeCode(vo.getTypeCode());
    result.setTypeDes(vo.getTypeDes());
    result.setTypeName(vo.getTypeName());
    result.setTypeOrder(vo.getTypeOrder());
    return result;
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysDictItemTypeDto getById(String id) {
    try {
      Optional<SysDictItemType> optional = dao.findById(id);
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<SysDictItemTypeDto> pageList(QueryCriteriaBean data) {
    try {
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data)));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysDictItemTypeDto> findList(QueryCriteriaBean data) {
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
  public long checkCode(String id, String code) {
    try {
      Criteria<SysDictItemType> criteria = new Criteria<>();
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
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true) })
  public int deleteById(String id) {
    QueryCriteriaBean data = new QueryCriteriaBean();
    List<AttributeBean> whereList = new ArrayList<>();
    AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, id);
    whereList.add(attribute);
    data.setWhereList(whereList);
    List<SysDictItemDto> dictItemList = sysDictItemService.findList(data);
    dictItemList.forEach(dictItem -> sysDictItemService.deleteById(dictItem.getId()));
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  public List<SysDictItemTypeDto> findListByCodes(List<String> codeList) {
    try {
      return convertDto(dao.findListByCode(codeList));
    } catch (Exception e) {
      LOGGER.error("Error findListByCodes: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true) })
  public Integer editStatus(String id, String status) {
    Optional<SysDictItemType> optional = dao.findById(id);
    if (optional.isPresent()) {
      SysDictItemType entity = optional.get();
      entity.setStatus(status);
      dao.save(entity);
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

}
