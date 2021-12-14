package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.enums.ResultEnum;
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
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data)));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  public List<SysLogDto> findList(QueryCriteriaBean data) {
    try {
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data)));
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  public SysLogDto getById(String id) {
    try {
      Optional<SysLog> optional = dao.findById(id);
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Async
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public SysLogDto save(SysLogVo vo) {
    SysLog entity = toPojo(vo);
    return convertDto(dao.save(entity));
  }
  
  /**
   * 转换对象
   * 
   * @return SysLog
   */
  private SysLog toPojo(SysLogVo vo) {
    SysLog result = new SysLog();
    result.setId(vo.getId());
    result.setCreateTime(vo.getCreateTime());
    result.setMethodName(vo.getMethodName());
    result.setOperationContext(vo.getOperationContext());
    result.setOperationIp(vo.getOperationIp());
    result.setOperationName(vo.getOperationName());
    result.setOperationPath(vo.getOperationPath());
    return result;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int deleteById(String id) {
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  public int deleteLog(LocalDateTime dateTime) {
    Criteria<SysLog> criteria = new Criteria<>();
    criteria.add(Restrictions.lte(SysConstants.CREATE_TIME, dateTime));
    List<SysLog> list = dao.findAll(criteria);
    dao.deleteAll(list);
    return ResultEnum.SUCCESS.getCode();
  }

}
