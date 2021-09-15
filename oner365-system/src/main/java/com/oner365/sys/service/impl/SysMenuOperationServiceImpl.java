package com.oner365.sys.service.impl;

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

import com.google.common.base.Strings;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.sys.dao.ISysMenuOperDao;
import com.oner365.sys.dao.ISysMenuOperationDao;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.service.ISysMenuOperationService;

/**
 * ISysMenuOperationService实现类
 * @author zhaoyong
 */
@Service
public class SysMenuOperationServiceImpl implements ISysMenuOperationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuOperationServiceImpl.class);

    private static final String CACHE_NAME = "SysMenuOperation";

    @Autowired
    private ISysMenuOperationDao menuOperationDao;

    @Autowired
    private ISysMenuOperDao menuOperDao;

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public Page<SysMenuOperation> pageList(QueryCriteriaBean data) {
        try {
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return menuOperationDao.findAll(QueryUtils.buildCriteria(data), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysMenuOperation> findList() {
        try {
            return menuOperationDao.findAll();
        } catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return new ArrayList<>();
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysMenuOperation getById(String id) {
        try {
            Optional<SysMenuOperation> optional = menuOperationDao.findById(id);
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
    public SysMenuOperation save(SysMenuOperation menuOperation) {
        if (Strings.isNullOrEmpty(menuOperation.getId())) {
            menuOperation.setStatus(PublicConstants.STATUS_YES);
            menuOperation.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        menuOperation.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return menuOperationDao.save(menuOperation);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteById(String id) {
        // 删除菜单与操作关联
        menuOperDao.deleteByOperationId(id);
        // 删除操作与角色关联
        // 删除操作
        menuOperationDao.deleteById(id);
        return 1;
    }
    
    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<String> selectByMenuId(String menuId) {
        return menuOperDao.selectByMenuId(menuId);
    }

    @Override
    public int checkType(String id, String type) {
        try {
            return menuOperationDao.countTypeById(id, type);
        } catch (Exception e) {
            LOGGER.error("Error selectByMenuId: ", e);
        }
        return 0;
    }

}
