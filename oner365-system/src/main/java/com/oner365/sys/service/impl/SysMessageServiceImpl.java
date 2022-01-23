package com.oner365.sys.service.impl;

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
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.dao.ISysMessageDao;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.entity.SysMessage;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.sys.vo.SysMessageVo;

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
  private ISysMessageDao sysMessageDao;

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysMessageDto save(SysMessageVo vo) {
    SysMessage entity = toPojo(vo);
    return convertDto(sysMessageDao.save(entity));
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
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysMessageDto getById(String id) {
    try {
      Optional<SysMessage> optional = sysMessageDao.findById(id);
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getById:", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, key = "#messageType")
  public List<SysMessageDto> findList(String messageType) {
    try {
      Criteria<SysMessage> criteria = new Criteria<>();
      criteria.add(Restrictions.eq("messageType", messageType));
      return convertDto(sysMessageDao.findAll(criteria));
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public int deleteById(String id) {
    sysMessageDao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

}
