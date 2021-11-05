package com.oner365.sys.service.impl;

import java.util.Collections;
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

import com.google.common.base.Strings;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysDictItemDao;
import com.oner365.sys.entity.SysDictItem;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.util.DataUtils;

/**
 * ISysDictItemService 实现类
 * @author zhaoyong
 */
@Service
public class SysDictItemServiceImpl implements ISysDictItemService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SysDictItemServiceImpl.class);

    private static final String CACHE_NAME = "SysDictItem";
    private static final String CACHE_TYPE_NAME = "SysDictItemType";

    @Autowired
    private ISysDictItemDao dao;

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_TYPE_NAME, allEntries = true)
    })
    public SysDictItem save(SysDictItem item) {
        return dao.save(item);
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysDictItem getById(String id) {
        Optional<SysDictItem> optional = dao.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<SysDictItem> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return dao.findAll(QueryUtils.buildCriteria(data), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }
    
    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysDictItem> findList(QueryCriteriaBean data) {
        try {
            if (data.getOrder() == null) {
                return dao.findAll(QueryUtils.buildCriteria(data));
            }
            return dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildSortRequest(data.getOrder()));
        } catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }
    
    @Override
    public long checkCode(String id, String typeId, String code) {
        try {
            Criteria<SysDictItem> criteria = new Criteria<>();
            criteria.add(Restrictions.eq(SysConstants.ITEM_CODE, DataUtils.trimToNull(code)));
            criteria.add(Restrictions.eq(SysConstants.TYPE_ID, DataUtils.trimToNull(typeId)));
            if (!Strings.isNullOrEmpty(id)) {
                criteria.add(Restrictions.ne(SysConstants.ID, id));
            }
            return dao.count(criteria);
        } catch (Exception e) {
            LOGGER.error("Error checkCode:", e);
        }
        return ExistsEnum.NO.getOrdinal();
    }
    
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_TYPE_NAME, allEntries = true)
    })
    public int deleteById(String id) {
        dao.deleteById(id);
        return ResultEnum.SUCCESS.getOrdinal();
    }
    
    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_TYPE_NAME, allEntries = true)
    })
    public Integer editStatus(String id, String status) {
        SysDictItem entity = this.getById(id);
        if (entity != null && entity.getId() != null) {
            entity.setStatus(status);
            this.save(entity);
            return ResultEnum.SUCCESS.getOrdinal();
        }
        return ResultEnum.ERROR.getOrdinal();
    }

}
