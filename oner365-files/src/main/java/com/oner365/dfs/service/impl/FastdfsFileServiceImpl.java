package com.oner365.dfs.service.impl;

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
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.dfs.dao.IFastdfsFileDao;
import com.oner365.dfs.entity.FastdfsFile;
import com.oner365.dfs.service.IFastdfsFileService;

/**
 * IFastdfsFileService实现类
 * @author zhaoyong
 */
@Service
public class FastdfsFileServiceImpl implements IFastdfsFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastdfsFileServiceImpl.class);

    private static final String CACHE_NAME = "FastdfsFile";

    @Autowired
    private IFastdfsFileDao dao;

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<FastdfsFile> pageList(QueryCriteriaBean data) {
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
    public List<FastdfsFile> findList(QueryCriteriaBean data) {
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
    public FastdfsFile getById(String id) {
        try {
            Optional<FastdfsFile> optional = dao.findById(id);
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
    public FastdfsFile save(FastdfsFile entity) {
        entity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return dao.save(entity);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteById(String id) {
        try {
            dao.deleteById(id);
            return PublicConstants.SUCCESS_CODE;
        } catch (Exception e) {
            LOGGER.error("Error deleteById: ", e);
        }
        return PublicConstants.ERROR_CODE;
    }

}
