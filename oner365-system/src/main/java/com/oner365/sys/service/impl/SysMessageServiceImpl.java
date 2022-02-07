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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.sys.dao.ISysMessageDao;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.entity.SysMessage;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.sys.vo.SysMessageVo;
import com.oner365.util.DataUtils;

/**
 * 消息接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysMessageServiceImpl implements ISysMessageService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysMessageServiceImpl.class);

  private static final String CACHE_NAME = "SysMessage";

  @Autowired
  private ISysMessageDao dao;

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<SysMessageDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysMessage> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convertDto(page);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMessageDto> findList(QueryCriteriaBean data) {
    try {
      if (data.getOrder() == null) {
        return convertDto(dao.findAll(QueryUtils.buildCriteria(data)));
      }
      List<SysMessage> list = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildSortRequest(data.getOrder()));
      return convertDto(list);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysMessageDto getById(String id) {
    try {
      Optional<SysMessage> optional = dao.findById(id);
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getById:", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysMessageDto save(SysMessageVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setCreateTime(LocalDateTime.now());
    }
    vo.setUpdateTime(LocalDateTime.now());
    SysMessage entity = toPojo(vo);
    return convertDto(dao.save(entity));
  }

  /**
   * 转换对象
   * 
   * @return SysMessage
   */
  private SysMessage toPojo(SysMessageVo vo) {
    SysMessage result = new SysMessage();
    result.setId(vo.getId());
    result.setContext(vo.getContext());
    result.setCreateTime(vo.getCreateTime());
    result.setMessageName(vo.getMessageName());
    result.setMessageType(vo.getMessageType());
    result.setQueueKey(vo.getQueueKey());
    result.setQueueType(vo.getQueueType());
    result.setReceiveUser(vo.getReceiveUser());
    result.setSendUser(vo.getSendUser());
    result.setTypeId(vo.getTypeId());
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
  public Integer editStatus(String id, MessageStatusEnum status) {
    Optional<SysMessage> optional = dao.findById(id);
    if (optional.isPresent()) {
      optional.get().setStatus(status);
      optional.get().setUpdateTime(LocalDateTime.now());
      dao.save(optional.get());
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

}
