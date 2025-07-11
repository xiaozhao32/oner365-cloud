package com.oner365.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.AttributeBean;
import com.oner365.data.jpa.query.Criteria;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.data.jpa.query.Restrictions;
import com.oner365.data.redis.annotation.GeneratorCache;
import com.oner365.data.redis.annotation.RedisCacheAble;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysDictItemTypeDao;
import com.oner365.sys.dto.SysDictItemDto;
import com.oner365.sys.dto.SysDictItemTypeDto;
import com.oner365.sys.entity.SysDictItemType;
import com.oner365.sys.service.ISysDictItemService;
import com.oner365.sys.service.ISysDictItemTypeService;
import com.oner365.sys.vo.SysDictItemTypeVo;

/**
 * 字典类型接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysDictItemTypeServiceImpl implements ISysDictItemTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysDictItemTypeServiceImpl.class);

    private static final String CACHE_NAME = "SysDictItemType";

    private static final String CACHE_ITEM_NAME = "SysDictItem";

    @Resource
    private ISysDictItemTypeDao dao;

    @Resource
    private ISysDictItemService sysDictItemService;

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true) })
    public SysDictItemTypeDto save(SysDictItemTypeVo vo) {
        if (DataUtils.isEmpty(vo.getId())) {
            vo.setId(vo.getTypeCode());
        }
        SysDictItemType entity = dao.save(convert(vo, SysDictItemType.class));
        return convert(entity, SysDictItemTypeDto.class);
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysDictItemTypeDto getById(String id) {
        try {
            Optional<SysDictItemType> optional = dao.findById(id);
            return convert(optional.orElse(null), SysDictItemTypeDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public PageInfo<SysDictItemTypeDto> pageList(QueryCriteriaBean data) {
        try {
            Page<SysDictItemType> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
            return convert(page, SysDictItemTypeDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public List<SysDictItemTypeDto> findList(QueryCriteriaBean data) {
        try {
            if (data.getOrder() == null) {
                return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysDictItemTypeDto.class);
            }
            List<SysDictItemType> list = dao.findAll(QueryUtils.buildCriteria(data),
                    Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
            return convert(list, SysDictItemTypeDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Boolean checkCode(String id, String code) {
        try {
            Criteria<SysDictItemType> criteria = new Criteria<>();
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
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true) })
    public Boolean deleteById(String id) {
        QueryCriteriaBean data = new QueryCriteriaBean();
        List<AttributeBean> whereList = new ArrayList<>();
        AttributeBean attribute = new AttributeBean(SysConstants.TYPE_ID, id);
        whereList.add(attribute);
        data.setWhereList(whereList);
        List<SysDictItemDto> dictItemList = sysDictItemService.findList(data);
        dictItemList.forEach(dictItem -> sysDictItemService.deleteById(dictItem.getId()));
        dao.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public List<SysDictItemTypeDto> findListByCodes(List<String> codeList) {
        try {
            Criteria<SysDictItemType> criteria = new Criteria<>();
            criteria.add(Restrictions.in(SysConstants.TYPE_CODE, codeList, false));
            return convert(dao.findAll(criteria), SysDictItemTypeDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error findListByCodes: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
            @CacheEvict(value = CACHE_ITEM_NAME, allEntries = true) })
    public Boolean editStatus(String id, StatusEnum status) {
        Optional<SysDictItemType> optional = dao.findById(id);
        if (optional.isPresent()) {
            SysDictItemType entity = optional.get();
            entity.setStatus(status);
            dao.save(entity);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
