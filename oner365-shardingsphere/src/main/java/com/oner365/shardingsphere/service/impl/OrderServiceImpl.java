package com.oner365.shardingsphere.service.impl;

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
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.exception.ProjectRuntimeException;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.data.jpa.query.QueryUtils;
import com.oner365.data.redis.annotation.GeneratorCache;
import com.oner365.data.redis.annotation.RedisCacheAble;
import com.oner365.shardingsphere.dao.IOrderDao;
import com.oner365.shardingsphere.dto.OrderDto;
import com.oner365.shardingsphere.entity.Order;
import com.oner365.shardingsphere.service.IOrderService;
import com.oner365.shardingsphere.vo.OrderVo;

/**
 * 订单实现类
 *
 * @author zhaoyong
 *
 */
@Service
public class OrderServiceImpl implements IOrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final String CACHE_NAME = "Order";

    @Resource
    private IOrderDao dao;

    @Override
    @GeneratorCache(CACHE_NAME)
    public PageInfo<OrderDto> pageList(QueryCriteriaBean data) {
        try {
            Page<Order> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
            return convert(page, OrderDto.class);
        }
        catch (Exception e) {
            logger.error("Error pageList: ", e);
        }
        return null;
    }

    @Override
    @GeneratorCache(CACHE_NAME)
    public List<OrderDto> findList(QueryCriteriaBean data) {
        try {
            if (data.getOrder() == null) {
                return convert(dao.findAll(QueryUtils.buildCriteria(data)), OrderDto.class);
            }
            List<Order> list = dao.findAll(QueryUtils.buildCriteria(data),
                    Objects.requireNonNull(QueryUtils.buildSortRequest(data.getOrder())));
            return convert(list, OrderDto.class);
        }
        catch (Exception e) {
            logger.error("Error findList: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public OrderDto getById(String id) {
        try {
            Optional<Order> optional = dao.findById(id);
            if (optional.isPresent()) {
                return convert(optional.orElse(null), OrderDto.class);
            }
        }
        catch (Exception e) {
            logger.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public OrderDto save(OrderVo vo) {
        if (DataUtils.isEmpty(vo.getId())) {
            vo.setStatus(StatusEnum.YES);
            vo.setCreateTime(LocalDateTime.now());
        }
        Order entity = dao.save(convert(vo, Order.class));
        return convert(entity, OrderDto.class);
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
        Optional<Order> optional = dao.findById(id);
        if (optional.isPresent()) {
            optional.get().setStatus(status);
            dao.save(optional.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
