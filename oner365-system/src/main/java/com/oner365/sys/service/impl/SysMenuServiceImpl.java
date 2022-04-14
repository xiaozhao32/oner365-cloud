package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysMenuDao;
import com.oner365.sys.dao.ISysMenuOperDao;
import com.oner365.sys.dao.ISysRoleMenuDao;
import com.oner365.sys.dto.SysMenuDto;
import com.oner365.sys.dto.TreeSelect;
import com.oner365.sys.entity.SysMenu;
import com.oner365.sys.entity.SysMenuOper;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.mapper.SysMenuMapper;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.sys.vo.SysMenuVo;
import com.oner365.util.DataUtils;

/**
 * 菜单接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuServiceImpl.class);

  private static final String CACHE_NAME = "SysMenu";
  private static final String CACHE_ROLE_NAME = "SysRole";

  @Autowired
  private ISysMenuDao menuDao;

  @Autowired
  private ISysMenuOperDao menuOperDao;

  @Autowired
  private ISysRoleMenuDao roleMenuDao;

  @Autowired
  private SysMenuMapper menuMapper;

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysMenuDto getById(String id) {
    try {
      Optional<SysMenu> optional = menuDao.findById(id);
      return convert(optional.orElse(null), SysMenuDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_ROLE_NAME, allEntries = true) })
  public SysMenuDto save(SysMenuVo vo) {
    vo.setStatus(StatusEnum.YES.getCode());
    vo.setCreateTime(LocalDateTime.now());
    vo.setUpdateTime(LocalDateTime.now());

    SysMenu menu = menuDao.save(convert(vo, SysMenu.class));

    menuOperDao.deleteByMenuId(menu.getId());
    if (!DataUtils.isEmpty(menu.getOperIds())) {
      menu.getOperIds().forEach(id -> {
        SysMenuOperation operation = new SysMenuOperation();
        operation.setId(id);
        SysMenuOper menuOper = new SysMenuOper();
        menuOper.setMenuId(menu.getId());
        menuOper.setSysMenuOperation(operation);
        menuOperDao.save(menuOper);
      });
    }
    return convert(menu, SysMenuDto.class);
  }
  
  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_ROLE_NAME, allEntries = true) })
  public int editStatusById(String id, String status) {
    Optional<SysMenu> optional = menuDao.findById(id);
    if (optional.isPresent()) {
      SysMenu entity = optional.get();
      entity.setStatus(status);
      menuDao.save(entity);
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuDto> findMenuByTypeCode(String typeCode) {
    try {
      return convert(menuDao.findMenuByTypeCode(typeCode), SysMenuDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findMenuByTypeCode: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuDto> selectMenuByRoles(List<String> roles, String menuTypeId, String parentId) {
    try {
      return convert(menuMapper.selectMenuByRoles(roles, menuTypeId, parentId), SysMenuDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findMenuByRoles: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuDto> findMenu(String menuTypeId, String parentId) {
    Criteria<SysMenu> criteria = new Criteria<>();
    criteria.add(Restrictions.eq("menuTypeId", menuTypeId));
    criteria.add(Restrictions.eq("parentId", parentId));
    criteria.add(Restrictions.eq(SysConstants.STATUS, StatusEnum.YES.getCode()));
    return convert(menuDao.findAll(criteria), SysMenuDto.class);
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<TreeSelect> buildTreeSelect(List<SysMenuDto> menus) {
    List<SysMenuDto> menuTrees = buildTree(menus);
    return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
  }

  @Override
  public List<SysMenuDto> buildTree(List<SysMenuDto> menus) {
    List<SysMenuDto> returnList = new ArrayList<>();
    for (SysMenuDto t : menus) {
      // 根据传入的某个父节点ID,遍历该父节点的所有子节点
      if (SysConstants.DEFAULT_PARENT_ID.equals(t.getParentId())) {
        recursionFn(menus, t);
        returnList.add(t);
      }
    }
    if (returnList.isEmpty()) {
      returnList = menus;
    }
    return returnList;
  }

  /**
   * 递归列表
   *
   * @param list 菜单列表
   * @param t    菜单
   */
  private void recursionFn(List<SysMenuDto> list, SysMenuDto t) {
    // 得到子节点列表
    List<SysMenuDto> childList = getChildList(list, t);
    if (!childList.isEmpty()) {
      t.setChildren(childList);
      for (SysMenuDto tChild : childList) {
        if (hasChild(list, tChild)) {
          // 判断是否有子节点
          for (SysMenuDto n : childList) {
            recursionFn(list, n);
          }
        }
      }
    }
  }

  /**
   * 得到子节点列表
   */
  private List<SysMenuDto> getChildList(List<SysMenuDto> list, SysMenuDto t) {
    return list.stream().filter(e -> e.getParentId().equals(t.getId())).collect(Collectors.toList());
  }

  /**
   * 判断是否有子节点
   */
  private boolean hasChild(List<SysMenuDto> list, SysMenuDto t) {
    return !getChildList(list, t).isEmpty();
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuDto> selectList(SysMenuVo vo) {
    List<SysMenu> list = menuMapper.selectList(convert(vo, SysMenu.class));
    return convert(list, SysMenuDto.class);
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysMenuDto> selectListByUserId(SysMenuVo vo) {
    List<SysMenu> list = menuMapper.selectListByUserId(convert(vo, SysMenu.class));
    return convert(list, SysMenuDto.class);
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<String> selectListByRoleId(String roleId, String menuTypeId) {
    return menuMapper.selectListByRoleId(roleId, menuTypeId);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_ROLE_NAME, allEntries = true) })
  public int deleteById(String id) {
    roleMenuDao.deleteByMenuId(id);
    menuDao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

}
