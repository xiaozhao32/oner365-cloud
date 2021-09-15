package com.oner365.sys.service.impl;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysDictItemTypeDao;
import com.oner365.sys.entity.SysDictItem;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.util.DataUtils;

/**
 * ISysDictItemTypeService实现类
 * @author zhaoyong
 */
@Service
public class SysDictItemTypeServiceImpl implements ISysDictItemTypeService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SysDictItemTypeServiceImpl.class);

    private static final String CACHE_NAME = "SysDictItemType";
    private static final String CACHE_ITEM_NAME = "SysDictItem";

    @Autowired
    private ISysDictItemTypeDao dao;
    
    @Autowired
    private ISysDictItemService sysDictItemService;

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true)
    })
    public SysDictItemType save(SysDictItemType type) {
        if (DataUtils.isEmpty(type.getId())) {
            type.setId(type.getTypeCode());
        }
        return dao.save(type);
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysDictItemType getById(String id) {
        Optional<SysDictItemType> optional = dao.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<SysDictItemType> pageList(QueryCriteriaBean data) {
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
    public List<SysDictItemType> findList(QueryCriteriaBean data) {
        try {
            if (data.getOrder() == null) {
                return dao.findAll(QueryUtils.buildCriteria(data));
            }
            return dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildSortRequest(data.getOrder()));
        } catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return new ArrayList<>();
    }
    
    @Override
    public int checkTypeId(String id, String code) {
        return dao.countTypeById(id, code);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true)
    })
    public int deleteById(String id) {
        QueryCriteriaBean data = new QueryCriteriaBean();
        List<AttributeBean> whereList = new ArrayList<>();
        AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, id);
        whereList.add(attribute);
        data.setWhereList(whereList);
        List<SysDictItem> dictItemList = sysDictItemService.findList(data);
        dictItemList.forEach(dictItem -> sysDictItemService.deleteById(dictItem.getId()));
        dao.deleteById(id);
        return 1;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysDictItemType> findListByCodes(JSONObject json) {
        JSONArray codeArray = json.getJSONArray("codes");
        if (!codeArray.isEmpty()) {
            List<String> codes = JSON.parseArray(codeArray.toJSONString(), String.class);
            return dao.findListByCode(codes);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true)
    })
    public Integer editStatus(String id, String status) {
        SysDictItemType entity = this.getById(id);
        if (entity != null && entity.getId() != null) {
            entity.setStatus(status);
            this.save(entity);
            return 1;
        }
        return 0;
    }

}
