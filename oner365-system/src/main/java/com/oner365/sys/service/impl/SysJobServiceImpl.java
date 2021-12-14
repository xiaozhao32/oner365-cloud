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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.sys.dao.ISysJobDao;
import com.oner365.sys.dto.SysJobDto;
import com.oner365.sys.entity.SysJob;
import com.oner365.sys.service.ISysJobService;
import com.oner365.sys.vo.SysJobVo;
import com.oner365.util.DataUtils;

/**
 * 职位接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysJobServiceImpl implements ISysJobService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysJobServiceImpl.class);

  private static final String CACHE_NAME = "SysJob";

  @Autowired
  private ISysJobDao dao;

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<SysJobDto> pageList(QueryCriteriaBean data) {
    try {
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data)));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysJobDto> findList(QueryCriteriaBean data) {
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
  public SysJobDto getById(String id) {
    try {
      Optional<SysJob> optional = dao.findById(id);
      if (optional.isPresent()) {
        return convertDto(optional.orElse(null));
      }
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysJobDto save(SysJobVo vo) {
    SysJob job = toPojo(vo);
    if (DataUtils.isEmpty(job.getId())) {
      job.setStatus(StatusEnum.YES.getCode());
      job.setCreateTime(LocalDateTime.now());
    }
    job.setUpdateTime(LocalDateTime.now());
    return convertDto(dao.save(job));
  }
  
  private SysJob toPojo(SysJobVo vo) {
    SysJob result = new SysJob();
    result.setCreateTime(vo.getCreateTime());
    result.setId(vo.getId());
    result.setJobInfo(vo.getJobInfo());
    result.setJobLogo(vo.getJobLogo());
    result.setJobLogoUrl(vo.getJobLogoUrl());
    result.setJobName(vo.getJobName());
    result.setJobOrder(vo.getJobOrder());
    result.setParentId(vo.getParentId());
    result.setStatus(vo.getStatus());
    result.setUpdateTime(vo.getUpdateTime());
    return result;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public int deleteById(String id) {
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Integer editStatus(String id, String status) {
    SysJob entity = dao.findById(id).orElse(null);
    if (entity != null && entity.getId() != null) {
      entity.setStatus(status);
      entity.setUpdateTime(LocalDateTime.now());
      dao.save(entity);
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

}
