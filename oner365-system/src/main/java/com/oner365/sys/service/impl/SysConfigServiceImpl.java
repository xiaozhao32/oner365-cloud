package com.oner365.sys.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.oner365.sys.dao.ISysConfigDao;
import com.oner365.sys.dto.SysConfigDto;
import com.oner365.sys.entity.SysConfig;
import com.oner365.sys.service.ISysConfigService;
import com.oner365.sys.vo.SysConfigVo;

/**
 * nt_sys_config Service 实现类
 *
 * @author zhaoyong
 *
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigServiceImpl.class);

    private static final String CACHE_NAME = "SysConfig";

    @Resource
    private ISysConfigDao dao;

    @Override
    @GeneratorCache(CACHE_NAME)
    public PageInfo<SysConfigDto> pageList(QueryCriteriaBean data) {
        try {
            Page<SysConfig> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
            return convert(page, SysConfigDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public List<SysConfigDto> findList(QueryCriteriaBean data) {
        try {
            if (data.getOrder() == null) {
                return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysConfigDto.class);
            }
            List<SysConfig> list = dao.findAll(QueryUtils.buildCriteria(data),
                    Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
            return convert(list, SysConfigDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysConfigDto getById(String id) {
        try {
            Optional<SysConfig> optional = dao.findById(id);
            if (optional.isPresent()) {
                return convert(optional.get(), SysConfigDto.class);
            }
        }
        catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public SysConfigDto save(SysConfigVo vo) {
        if (DataUtils.isEmpty(vo.getId())) {
            vo.setStatus(StatusEnum.YES);
        }
        SysConfig entity = dao.save(convert(vo, SysConfig.class));
        return convert(entity, SysConfigDto.class);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Boolean deleteById(String id) {
        dao.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public Boolean editStatus(String id, StatusEnum status) {
        Optional<SysConfig> optional = dao.findById(id);
        if (optional.isPresent()) {
            optional.get().setStatus(status);
            dao.save(optional.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean checkConfigName(String id, String configName) {
        try {
            Criteria<SysConfig> criteria = new Criteria<>();
            criteria.add(Restrictions.eq(SysConstants.CONFIG_NAME, DataUtils.trimToNull(configName)));
            if (!DataUtils.isEmpty(id)) {
                criteria.add(Restrictions.ne(SysConstants.ID, id));
            }
            if (dao.count(criteria) > 0) {
                return Boolean.TRUE;
            }
        }
        catch (Exception e) {
            LOGGER.error("Error checkConfigName:", e);
        }
        return Boolean.FALSE;
    }

}
