package com.oner365.files.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.files.dao.IFileStorageDao;
import com.oner365.files.entity.SysFileStorage;
import com.oner365.files.service.IFileStorageService;

/**
 * IFileStorageService实现类
 * @author zhaoyong
 */
@Service
public class FileStorageServiceImpl implements IFileStorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private static final String CACHE_NAME = "FileStorage";

    @Autowired
    private IFileStorageDao dao;

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<SysFileStorage> pageList(QueryCriteriaBean data) {
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
    public List<SysFileStorage> findList(QueryCriteriaBean data) {
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
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysFileStorage getById(String id) {
        try {
            Optional<SysFileStorage> optional = dao.findById(id);
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
    public SysFileStorage save(SysFileStorage entity) {
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return dao.save(entity);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteById(String id) {
        try {
            dao.deleteById(id);
            return ResultEnum.SUCCESS.getOrdinal();
        } catch (Exception e) {
            LOGGER.error("Error deleteById: ", e);
        }
        return ResultEnum.ERROR.getOrdinal();
    }

}
