package com.oner365.sys.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysJobDao;
import com.oner365.sys.entity.SysJob;
import com.oner365.sys.service.ISysJobService;
import com.google.common.base.Strings;

/**
 * ISysJobService实现类
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
    public Page<SysJob> pageList(JSONObject paramJson) {
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
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysJob> findList(JSONObject paramJson) {
        Criteria<SysJob> criteria = getCriteria(paramJson);
        criteria.add(Restrictions.eq(SysConstants.STATUS, PublicConstants.STATUS_YES));
        return dao.findAll(criteria, Sort.by(SysConstants.JOB_ORDER));
    }
    
    private Criteria<SysJob> getCriteria(JSONObject paramJson) {
        Criteria<SysJob> criteria = new Criteria<>();
        criteria.add(Restrictions.eq(SysConstants.STATUS, paramJson.getString(SysConstants.STATUS)));
        criteria.add(Restrictions.like(SysConstants.JOB_NAME, paramJson.getString(SysConstants.JOB_NAME)));
        return criteria;
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysJob getById(String id) {
        try {
            Optional<SysJob> optional = dao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public SysJob saveJob(SysJob job) {
        if (Strings.isNullOrEmpty(job.getId())) {
            job.setStatus(PublicConstants.STATUS_YES);
            job.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        job.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return dao.save(job);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteById(String id) {
        dao.deleteById(id);
        return 1;
    }
    
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Integer editStatus(String id, String status) {
        SysJob entity = this.getById(id);
        if (entity.getId() != null) {
            entity.setStatus(status);
            this.saveJob(entity);
            return 1;
        }
        return 0;
    }

}
