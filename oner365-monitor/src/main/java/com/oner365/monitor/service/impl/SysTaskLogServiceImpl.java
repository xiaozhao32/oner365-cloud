package com.oner365.monitor.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.oner365.api.rabbitmq.dto.SysTaskLogDto;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.monitor.dao.ISysTaskLogDao;
import com.oner365.monitor.entity.SysTaskLog;
import com.oner365.monitor.mapper.SysTaskLogMapper;
import com.oner365.monitor.service.ISysTaskLogService;
import com.oner365.monitor.vo.SysTaskLogVo;

/**
 * 定时任务调度日志信息 服务层
 *
 * @author liutao
 */
@Service
public class SysTaskLogServiceImpl implements ISysTaskLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysTaskLogServiceImpl.class);

    @Resource
    private ISysTaskLogDao dao;

    @Resource
    private SysTaskLogMapper taskLogMapper;

    @Override
    public PageInfo<SysTaskLogDto> pageList(QueryCriteriaBean data) {
        try {
            Page<SysTaskLog> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
            return convert(page, SysTaskLogDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    public List<SysTaskLogDto> findList(QueryCriteriaBean data) {
        try {
            if (data.getOrder() == null) {
                return convert(dao.findAll(QueryUtils.buildCriteria(data)), SysTaskLogDto.class);
            }
            List<SysTaskLog> list = dao.findAll(QueryUtils.buildCriteria(data),
                    Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
            return convert(list, SysTaskLogDto.class);
        }
        catch (Exception e) {
            LOGGER.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public SysTaskLogDto selectTaskLogById(String id) {
        Optional<SysTaskLog> optional = dao.findById(id);
        return convert(optional.orElse(null), SysTaskLogDto.class);
    }

    @Override
    public Boolean addTaskLog(SysTaskLogVo vo) {
        if (DataUtils.isEmpty(vo.getId())) {
            vo.setCreateTime(DateUtil.getDate());
        }
        dao.save(convert(vo, SysTaskLog.class));
        return Boolean.TRUE;
    }

    @Override
    public List<Boolean> deleteTaskLogByIds(String[] ids) {
        return Arrays.stream(ids).map(this::deleteTaskLogById).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteTaskLogById(String id) {
        try {
            dao.deleteById(id);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            LOGGER.error("Error deleteTaskLogById:", e);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean cleanTaskLog() {
        taskLogMapper.cleanTaskLog();
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteTaskLogByCreateTime(String time) {
        try {
            dao.deleteTaskLogByCreateTime(time);
            return Boolean.TRUE;
        }
        catch (Exception e) {
            LOGGER.error("Error deleteTaskLogByCreateTime: ", e);
        }
        return Boolean.FALSE;
    }

}
