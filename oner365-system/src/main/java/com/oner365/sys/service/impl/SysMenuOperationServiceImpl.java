package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
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
import com.oner365.sys.dao.ISysMenuOperDao;
import com.oner365.sys.dao.ISysMenuOperationDao;
import com.oner365.sys.dto.SysMenuOperationDto;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.vo.SysMenuOperationVo;

/**
 * 菜单操作接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysMenuOperationServiceImpl implements ISysMenuOperationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuOperationServiceImpl.class);

  private static final String CACHE_NAME = "SysMenuOperation";

  @Resource
  private ISysMenuOperationDao menuOperationDao;

  @Resource
  private ISysMenuOperDao menuOperDao;

  @Override
  @GeneratorCache(CACHE_NAME)
  public PageInfo<SysMenuOperationDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysMenuOperation> page = menuOperationDao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysMenuOperationDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<SysMenuOperationDto> findList(QueryCriteriaBean data) {
    try {
      List<SysMenuOperation> list = menuOperationDao.findAll(QueryUtils.buildCriteria(data));
      return convert(list, SysMenuOperationDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysMenuOperationDto getById(String id) {
    try {
      Optional<SysMenuOperation> optional = menuOperationDao.findById(id);
      return convert(optional.orElse(null), SysMenuOperationDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysMenuOperationDto save(SysMenuOperationVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setStatus(StatusEnum.YES);
      vo.setCreateTime(LocalDateTime.now());
    }
    vo.setUpdateTime(LocalDateTime.now());
    SysMenuOperation entity = menuOperationDao.save(convert(vo, SysMenuOperation.class));
    return convert(entity, SysMenuOperationDto.class);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Boolean deleteById(String id) {
    // 删除菜单与操作关联
    menuOperDao.deleteByOperationId(id);
    // 删除操作与角色关联
    // 删除操作
    menuOperationDao.deleteById(id);
    return Boolean.TRUE;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<String> selectByMenuId(String menuId) {
    return menuOperDao.selectByMenuId(menuId);
  }

  @Override
  public Boolean checkCode(String id, String code) {
    try {
      Criteria<SysMenuOperation> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(SysConstants.OPERATION_TYPE, DataUtils.trimToNull(code)));
      if (!DataUtils.isEmpty(id)) {
        criteria.add(Restrictions.ne(SysConstants.ID, id));
      }
      if (menuOperationDao.count(criteria) > 0) {
        return Boolean.TRUE;
      }
    } catch (Exception e) {
      LOGGER.error("Error checkCode:", e);
    }
    return Boolean.FALSE;
  }
  
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public Boolean editStatus(String id, StatusEnum status) {
    Optional<SysMenuOperation> optional = menuOperationDao.findById(id);
    if (optional.isPresent()) {
      optional.get().setStatus(status);
      optional.get().setUpdateTime(LocalDateTime.now());
      menuOperationDao.save(optional.get());
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

}
