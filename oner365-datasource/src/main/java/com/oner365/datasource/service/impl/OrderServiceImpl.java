package com.oner365.datasource.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.datasource.dao.IOrderDao;
import com.oner365.datasource.dto.OrderDto;
import com.oner365.datasource.entity.Order;
import com.oner365.datasource.service.IOrderService;
import com.oner365.datasource.vo.OrderVo;
import com.oner365.util.DataUtils;

/**
 * 订单实现类
 * 
 * @author zhaoyong
 *
 */
@Service
public class OrderServiceImpl implements IOrderService {

  private final Logger logger = LoggerFactory.getLogger(IOrderService.class);

  private static final String CACHE_NAME = "Order";

  @Autowired
  private IOrderDao dao;

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<OrderDto> pageList(QueryCriteriaBean data) {
    try {
      Page<Order> page = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, OrderDto.class);
    } catch (Exception e) {
      logger.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<OrderDto> findList(QueryCriteriaBean data) {
    try {
      if (data.getOrder() == null) {
        return convert(dao.findAll(QueryUtils.buildCriteria(data)), OrderDto.class);
      }
      List<Order> list = dao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildSortRequest(data.getOrder()));
      return convert(list, OrderDto.class);
    } catch (Exception e) {
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
    } catch (Exception e) {
      logger.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public OrderDto save(OrderVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setStatus(StatusEnum.YES.getCode());
      vo.setCreateTime(LocalDateTime.now());
    }
    Order entity = dao.save(convert(vo, Order.class));
    return convert(entity, OrderDto.class);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public int deleteById(String id) {
    dao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Integer editStatus(String id, String status) {
    Optional<Order> optional = dao.findById(id);
    if (optional.isPresent()) {
      optional.get().setStatus(status);
      dao.save(optional.get());
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

}