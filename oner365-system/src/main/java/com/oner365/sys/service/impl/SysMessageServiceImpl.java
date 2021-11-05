package com.oner365.sys.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.sys.dao.ISysMessageDao;
import com.oner365.sys.entity.SysMessage;
import com.oner365.sys.service.ISysMessageService;

/**
 * ISysMessageService实现类
 * @author zhaoyong
 */
@Service
public class SysMessageServiceImpl implements ISysMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMessageServiceImpl.class);

    private static final String CACHE_NAME = "SysMessage";

    @Autowired
    private ISysMessageDao dao;

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public SysMessage save(SysMessage sysMessage) {
        return dao.save(sysMessage);
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysMessage getById(String id) {
        try {
            Optional<SysMessage> optional = dao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById:", e);
        }
        return null;
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "#messageType")
    public List<SysMessage> findList(String messageType) {
        try {
            return dao.findList(messageType);
        } catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteById(String id) {
        dao.deleteById(id);
        return ResultEnum.SUCCESS.getOrdinal();
    }

}
