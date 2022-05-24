package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysLogDao;
import com.oner365.sys.dto.SysLogDto;
import com.oner365.sys.entity.SysLog;
import com.oner365.sys.service.ISysLogService;
import com.oner365.sys.vo.SysLogVo;

/**
 * 系统日志接口实现类
 *
 * @author zhaoyong
 */
@Component
public class SysLogServiceImpl implements ISysLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysLogServiceImpl.class);

  @Autowired
  private ISysLogDao dao;

  @Override
  public PageInfo<SysLogDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysLog> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysLogDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  public List<SysLogDto> findList(QueryCriteriaBean data) {
    try {
      return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysLogDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  public SysLogDto getById(String id) {
    try {
      Optional<SysLog> optional = dao.findById(id);
      return convert(optional.orElse(null), SysLogDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Async
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public void save(SysLogVo vo) {
    dao.save(convert(vo, SysLog.class));
  }
  
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean deleteById(String id) {
    dao.deleteById(id);
    return Boolean.TRUE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public Boolean deleteLog(LocalDateTime dateTime) {
    Criteria<SysLog> criteria = new Criteria<>();
    criteria.add(Restrictions.lte(SysConstants.CREATE_TIME, dateTime));
    List<SysLog> list = dao.findAll(criteria);
    dao.deleteAll(list);
    return Boolean.TRUE;
  }

}
