package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.Criteria;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.data.jpa.query.Restrictions;
import com.oner365.data.redis.annotation.GeneratorCache;
import com.oner365.data.redis.annotation.RedisCacheAble;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysMenuTypeDao;
import com.oner365.sys.dto.SysMenuTypeDto;
import com.oner365.sys.entity.SysMenuType;
import com.oner365.sys.service.ISysMenuTypeService;
import com.oner365.sys.vo.SysMenuTypeVo;

/**
 * SysMenuType Service
 *
 * @author zhaoyong
 */
@Service
public class SysMenuTypeServiceImpl implements ISysMenuTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuTypeServiceImpl.class);

    private static final String CACHE_NAME = "SysMenuType";

    private static final String CACHE_MENU_NAME = "SysMenu";

    @Resource
    private ISysMenuTypeDao dao;

    @Override
    @GeneratorCache(CACHE_NAME)
    public PageInfo<SysMenuTypeDto> pageList(QueryCriteriaBean data) {
        try {
            Page<SysMenuType> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
            return convert(page, SysMenuTypeDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public List<SysMenuTypeDto> findList(QueryCriteriaBean data) {
        try {
            return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysMenuTypeDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysMenuTypeDto getById(String id) {
        try {
            Optional<SysMenuType> optional = dao.findById(id);
            return convert(optional.orElse(null), SysMenuTypeDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
    public SysMenuTypeDto save(SysMenuTypeVo vo) {
        if (DataUtils.isEmpty(vo.getId())) {
            vo.setStatus(StatusEnum.YES);
            vo.setCreateTime(LocalDateTime.now());
        }
        else {
            vo.setUpdateTime(LocalDateTime.now());
        }
        SysMenuType entity = dao.save(convert(vo, SysMenuType.class));
        return convert(entity, SysMenuTypeDto.class);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
    public Boolean editStatus(String id, StatusEnum status) {
        Optional<SysMenuType> optional = dao.findById(id);
        if (optional.isPresent()) {
            SysMenuType entity = optional.get();
            entity.setStatus(status);
            dao.save(entity);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean checkCode(String id, String code) {
        try {
            Criteria<SysMenuType> criteria = new Criteria<>();
            criteria.add(Restrictions.eq(SysConstants.TYPE_CODE, DataUtils.trimToNull(code)));
            if (!DataUtils.isEmpty(id)) {
                criteria.add(Restrictions.ne(SysConstants.ID, id));
            }
            if (dao.count(criteria) > 0) {
                return Boolean.TRUE;
            }
        }
        catch (Exception e) {
            LOGGER.error("Error checkCode:", e);
        }
        return Boolean.FALSE;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public SysMenuTypeDto getMenuTypeByTypeCode(String typeCode) {
        Assert.notNull(typeCode, "typeCode is not empty.");
        Criteria<SysMenuType> criteria = new Criteria<>();
        criteria.add(Restrictions.eq(SysConstants.TYPE_CODE, typeCode));
        return convert(dao.findOne(criteria), SysMenuTypeDto.class);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
    public Boolean deleteById(String id) {
        dao.deleteById(id);
        return Boolean.TRUE;
    }

}
