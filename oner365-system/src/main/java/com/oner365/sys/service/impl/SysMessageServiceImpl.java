package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
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
import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.data.redis.annotation.GeneratorCache;
import com.oner365.data.redis.annotation.RedisCacheAble;
import com.oner365.sys.dao.ISysMessageDao;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.entity.SysMessage;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.service.ISysMessageService;
import com.oner365.sys.vo.SysMessageVo;

/**
 * 消息接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysMessageServiceImpl implements ISysMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMessageServiceImpl.class);

    private static final String CACHE_NAME = "SysMessage";

    @Resource
    private ISysMessageDao dao;

    @Override
    @GeneratorCache(CACHE_NAME)
    public PageInfo<SysMessageDto> pageList(QueryCriteriaBean data) {
        try {
            Page<SysMessage> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
            return convert(page, SysMessageDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public List<SysMessageDto> findList(QueryCriteriaBean data) {
        try {
            if (data.getOrder() == null) {
                return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysMessageDto.class);
            }
            List<SysMessage> list = dao.findAll(QueryUtils.buildCriteria(data),
                    Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
            return convert(list, SysMessageDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysMessageDto getById(String id) {
        try {
            Optional<SysMessage> optional = dao.findById(id);
            return convert(optional.orElse(null), SysMessageDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error getById:", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public SysMessageDto save(SysMessageVo vo) {
        if (DataUtils.isEmpty(vo.getId())) {
            vo.setCreateTime(LocalDateTime.now());
        }
        vo.setUpdateTime(LocalDateTime.now());
        SysMessage entity = dao.save(convert(vo, SysMessage.class));
        return convert(entity, SysMessageDto.class);
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
    public Boolean editStatus(String id, MessageStatusEnum status) {
        Optional<SysMessage> optional = dao.findById(id);
        if (optional.isPresent()) {
            optional.get().setStatus(status);
            optional.get().setUpdateTime(LocalDateTime.now());
            dao.save(optional.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
