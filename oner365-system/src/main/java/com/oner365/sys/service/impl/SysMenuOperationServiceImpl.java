package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
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
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysMenuOperDao;
import com.oner365.sys.dao.ISysMenuOperationDao;
import com.oner365.sys.dto.SysMenuOperationDto;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.vo.SysMenuOperationVo;
import com.oner365.util.DataUtils;

/**
 * 菜单操作接口实现类
 *
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
  public PageInfo<SysMenuOperationDto> pageList(QueryCriteriaBean data) {
    try {
      return convertDto(menuOperationDao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data)));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuOperationDto> findList() {
    try {
      return convertDto(menuOperationDao.findAll());
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
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @CacheEvict(value = CACHE_NAME, allEntries = true)
  public SysMenuOperationDto save(SysMenuOperationVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setStatus(StatusEnum.YES.getCode());
      vo.setCreateTime(LocalDateTime.now());
    }
    vo.setUpdateTime(LocalDateTime.now());
    SysMenuOperation entity = toPojo(vo);
    return convertDto(menuOperationDao.save(entity));
  }

  /**
   * 转换对象
   * 
   * @return SysMenuOperation
   */
  private SysMenuOperation toPojo(SysMenuOperationVo vo) {
    SysMenuOperation result = new SysMenuOperation();
    result.setId(vo.getId());
    result.setCreateTime(vo.getCreateTime());
    result.setOperationName(vo.getOperationName());
    result.setOperationType(vo.getOperationType());
    result.setStatus(vo.getStatus());
    result.setUpdateTime(vo.getUpdateTime());
    return result;
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
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<String> selectByMenuId(String menuId) {
    return menuOperDao.selectByMenuId(menuId);
  }

  @Override
  public long checkCode(String id, String code) {
    try {
      Criteria<SysMenuOperation> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(SysConstants.OPERATION_TYPE, DataUtils.trimToNull(code)));
      if (!DataUtils.isEmpty(id)) {
        criteria.add(Restrictions.ne(SysConstants.ID, id));
      }
      return menuOperationDao.count(criteria);
    } catch (Exception e) {
      LOGGER.error("Error checkCode:", e);
    }
    return ExistsEnum.NO.getCode();
  }

}
