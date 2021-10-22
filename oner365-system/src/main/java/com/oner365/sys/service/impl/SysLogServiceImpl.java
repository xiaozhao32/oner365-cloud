package com.oner365.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysLogDao;
import com.oner365.sys.entity.SysLog;
import com.oner365.sys.service.ISysLogService;

/**
 * 系统日志实现类
 * @author zhaoyong
 */
@Component
public class SysLogServiceImpl implements ISysLogService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogServiceImpl.class);
    
    @Autowired
    private ISysLogDao dao;

    @Override
    public Page<SysLog> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return dao.findAll(QueryUtils.buildCriteria(data), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    public List<SysLog> findList(QueryCriteriaBean data) {
        try {
            return dao.findAll(QueryUtils.buildCriteria(data));
        } catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return new ArrayList<>();   
    }
    
    @Override
    public SysLog getById(String id) {
        try {
            Optional<SysLog> optional = dao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public SysLog save(SysLog sysLog) {
        return dao.save(sysLog);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    public int deleteById(String id) {
        dao.deleteById(id);
        return PublicConstants.SUCCESS_CODE;
    }

    @Override
    public int deleteLog(Date date) {
        Criteria<SysLog> criteria = new Criteria<>();
        criteria.add(Restrictions.lte(SysConstants.CREATE_TIME, date));
        List<SysLog> list = dao.findAll(criteria);
        dao.deleteAll(list);
        return PublicConstants.SUCCESS_CODE;
    }

}
