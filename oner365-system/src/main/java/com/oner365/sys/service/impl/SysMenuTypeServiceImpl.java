package com.oner365.sys.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.oner365.sys.dao.ISysMenuTypeDao;
import com.oner365.sys.entity.SysMenuType;
import com.oner365.sys.service.ISysMenuTypeService;
import com.google.common.base.Strings;

/**
 * ISysMenuTypeService实现类
 * @author zhaoyong
 */
@Service
public class SysMenuTypeServiceImpl implements ISysMenuTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuTypeServiceImpl.class);

    private static final String CACHE_NAME = "SysMenuType";
    private static final String CACHE_MENU_NAME = "SysMenu";

    @Autowired
    private ISysMenuTypeDao dao;

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<SysMenuType> pageList(JSONObject paramJson) {
        try {
            QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
            Pageable pageable = QueryUtils.buildPageRequest(data);
            Criteria<SysMenuType> criteria = new Criteria<>();
            criteria.add(Restrictions.eq(SysConstants.TYPE_CODE, paramJson.getString(SysConstants.TYPE_CODE)));
            criteria.add(Restrictions.like(SysConstants.TYPE_NAME, paramJson.getString(SysConstants.TYPE_NAME)));
            criteria.add(Restrictions.eq(SysConstants.STATUS, paramJson.getString(SysConstants.STATUS)));
            return dao.findAll(criteria, pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysMenuType> findList(JSONObject paramJson) {
        Criteria<SysMenuType> criteria = new Criteria<>();
        criteria.add(Restrictions.eq(SysConstants.STATUS, paramJson.getString(SysConstants.STATUS)));
        return dao.findAll(criteria);
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysMenuType getById(String id) {
        try {
            Optional<SysMenuType> optional = dao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public SysMenuType save(SysMenuType menuType) {
        if (Strings.isNullOrEmpty(menuType.getId())) {
            menuType.setStatus(PublicConstants.STATUS_YES);
            menuType.setCreateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            menuType.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        }
        return dao.save(menuType);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public int editStatusById(String id, String status) {
        SysMenuType entity = getById(id);
        entity.setStatus(status);
        save(entity);
        return 1;
    }

    @Override
    public int checkCode(String id, String code) {
        try {
            return dao.countTypeById(id, code);
        } catch (Exception e) {
            LOGGER.error("Error checkCode: ", e);
        }
        return 0;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public SysMenuType getMenuTypeByTypeCode(String menuType) {
        return dao.getMenuTypeByTypeCode(menuType);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public int deleteById(String id) {
        dao.deleteById(id);
        return 1;
    }

}
