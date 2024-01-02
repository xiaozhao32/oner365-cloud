package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.data.redis.annotation.GeneratorCache;
import com.oner365.data.redis.annotation.RedisCacheAble;
import com.oner365.sys.dao.ISysJobDao;
import com.oner365.sys.dto.SysJobDto;
import com.oner365.sys.entity.SysJob;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.vo.SysJobVo;

/**
 * 职位接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysJobServiceImpl implements ISysJobService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysJobServiceImpl.class);

  private static final String CACHE_NAME = "SysJob";

  @Resource
  private ISysJobDao dao;

  @Override
  @GeneratorCache(CACHE_NAME)
  public PageInfo<SysJobDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysJob> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysJobDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<SysJobDto> findList(QueryCriteriaBean data) {
    try {
      if (data.getOrder() == null) {
        return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysJobDto.class);
      }
      List<SysJob> list = dao.findAll(QueryUtils.buildCriteria(data), 
          Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
      return convert(list, SysJobDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysJobDto getById(String id) {
    try {
      Optional<SysJob> optional = dao.findById(id);
      if (optional.isPresent()) {
        return convert(optional.orElse(null), SysJobDto.class);
      }
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysJobDto save(SysJobVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setStatus(StatusEnum.YES);
      vo.setCreateTime(LocalDateTime.now());
    }
    vo.setUpdateTime(LocalDateTime.now());
    SysJob entity = dao.save(convert(vo, SysJob.class));
    return convert(entity, SysJobDto.class);
  }
  
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Boolean deleteById(String id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Boolean editStatus(String id, StatusEnum status) {
    Optional<SysJob> optional = dao.findById(id);
    if (optional.isPresent()) {
      optional.get().setStatus(status);
      optional.get().setUpdateTime(LocalDateTime.now());
      dao.save(optional.get());
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

}
