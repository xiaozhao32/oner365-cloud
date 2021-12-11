package com.oner365.monitor.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.monitor.dao.ISysTaskLogDao;
import com.oner365.monitor.dto.SysTaskLogDto;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.mapper.SysTaskLogMapper;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author liutao
 */
@Service
public class SysTaskLogServiceImpl implements ISysTaskLogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysTaskLogServiceImpl.class);

  @Autowired
  private ISysTaskLogDao dao;

  @Autowired
  private SysTaskLogMapper taskLogMapper;

  @Override
  public Page<SysTaskLogDto> pageList(QueryCriteriaBean data) {
    try {
      Pageable pageable = QueryUtils.buildPageRequest(data);
      return convertDto(dao.findAll(QueryUtils.buildCriteria(data), pageable));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  public SysTaskLogDto selectTaskLogById(String id) {
    Optional<SysTaskLog> optional = dao.findById(id);
    return convertDto(optional.orElse(null));
  }

  @Override
  public void addTaskLog(SysTaskLogVo taskLog) {
    if (DataUtils.isEmpty(taskLog.getId())) {
      taskLog.setCreateTime(DateUtil.getDate());
    }
    SysTaskLog entity = toPojo(taskLog);
    dao.save(entity);
  }

  private SysTaskLog toPojo(SysTaskLogVo vo) {
    SysTaskLog result = new SysTaskLog();
    result.setCreateTime(vo.getCreateTime());
    result.setCreateUser(vo.getCreateUser());
    result.setExceptionInfo(vo.getExceptionInfo());
    result.setExecuteIp(vo.getExecuteIp());
    result.setExecuteServerName(vo.getExecuteServerName());
    result.setId(vo.getId());
    result.setInvokeTarget(vo.getInvokeTarget());
    result.setRemark(vo.getRemark());
    result.setStartTime(vo.getStartTime());
    result.setStatus(vo.getStatus());
    result.setStopTime(vo.getStopTime());
    result.setTaskGroup(vo.getTaskGroup());
    result.setTaskMessage(vo.getTaskMessage());
    result.setTaskName(vo.getTaskName());
    result.setUpdateTime(vo.getUpdateTime());
    return result;
  }

  @Override
  public int deleteTaskLogByIds(String[] ids) {
    int result = ResultEnum.ERROR.getCode();
    for (String id : ids) {
      result = deleteTaskLogById(id);
    }
    return result;
  }

  @Override
  public int deleteTaskLogById(String id) {
    try {
      dao.deleteById(id);
      return ResultEnum.SUCCESS.getCode();
    } catch (Exception e) {
      LOGGER.error("Error deleteTaskLogById:", e);
    }
    return ResultEnum.ERROR.getCode();
  }

  @Override
  public void cleanTaskLog() {
    taskLogMapper.cleanTaskLog();
  }

  @Override
  public String deleteTaskLogByCreateTime(String time) {
    try {
      dao.deleteTaskLogByCreateTime(time);
      return StatusEnum.YES.getCode();
    } catch (Exception e) {
      LOGGER.error("Error deleteTaskLogByCreateTime: ", e);
    }
    return StatusEnum.NO.getCode();
  }
}
