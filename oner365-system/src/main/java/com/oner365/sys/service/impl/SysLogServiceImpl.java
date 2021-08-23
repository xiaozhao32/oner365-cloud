package com.oner365.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysLogDao;
import com.oner365.sys.entity.SysLog;
import com.oner365.sys.service.ISysLogService;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<SysLog> pageList(JSONObject paramJson) {
        try {
            QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return dao.findAll(getCriteria(paramJson), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    public List<SysLog> findList(JSONObject paramJson) {
        return dao.findAll(getCriteria(paramJson));
    }
    
    private Criteria<SysLog> getCriteria(JSONObject paramJson) {
        Criteria<SysLog> criteria = new Criteria<>();
        criteria.add(Restrictions.like("methodName", paramJson.getString("methodName")));
        criteria.add(Restrictions.like("operationName", paramJson.getString("operationName")));
        criteria.add(Restrictions.like("operationIp", paramJson.getString("operationIp")));
        return criteria;
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
        return 1;
    }

    @Override
    public int deleteLog(Date date) {
        Criteria<SysLog> criteria = new Criteria<>();
        criteria.add(Restrictions.lte(SysConstants.CREATE_TIME, date));
        List<SysLog> list = dao.findAll(criteria);
        dao.deleteAll(list);
        return 1;
    }

}
