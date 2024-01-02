package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
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
import com.oner365.sys.dao.ISysRoleDao;
import com.oner365.sys.dao.ISysRoleMenuDao;
import com.oner365.sys.dao.ISysRoleMenuOperDao;
import com.oner365.sys.dao.ISysUserRoleDao;
import com.oner365.sys.dto.SysMenuDto;
import com.oner365.sys.dto.SysMenuIconDto;
import com.oner365.sys.dto.SysMenuOperDto;
import com.oner365.sys.dto.SysMenuTreeDto;
import com.oner365.sys.dto.SysRoleDto;
import com.oner365.sys.entity.SysRole;
import com.oner365.sys.entity.SysRoleMenu;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.vo.SysRoleVo;

/**
 * 系统角色接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleServiceImpl.class);

  private static final String CACHE_NAME = "SysRole";
  private static final String CACHE_MENU_NAME = "SysMenu";

  @Resource
  private ISysRoleDao roleDao;

  @Resource
  private ISysRoleMenuDao roleMenuDao;

  @Resource
  private ISysRoleMenuOperDao roleMenuOperDao;

  @Resource
  private ISysUserRoleDao userRoleDao;

  @Resource
  private ISysMenuService sysMenuService;

  @Override
  @GeneratorCache(CACHE_NAME)
  public PageInfo<SysRoleDto> pageList(QueryCriteriaBean data) {
    try {
      Page<SysRole> page = roleDao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data));
      return convert(page, SysRoleDto.class);
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<SysRoleDto> findList(QueryCriteriaBean data) {
    try {
      return convert(roleDao.findAll(QueryUtils.buildCriteria(data)), SysRoleDto.class);
    } catch (Exception e) {
      LOGGER.error("Error findList: ", e);
    }
    return Collections.emptyList();
  }

  @Override
  @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  public SysRoleDto getById(String id) {
    try {
      Optional<SysRole> optional = roleDao.findById(id);
      return convert(optional.orElse(null), SysRoleDto.class);
    } catch (Exception e) {
      LOGGER.error("Error getInfoById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public SysRoleDto save(SysRoleVo vo) {
    if (DataUtils.isEmpty(vo.getId())) {
      vo.setStatus(StatusEnum.YES);
      vo.setCreateTime(LocalDateTime.now());
    }
    if (DataUtils.isEmpty(vo.getRoleCode())) {
      vo.setRoleCode(String.valueOf(System.currentTimeMillis()));
    }
    vo.setUpdateTime(LocalDateTime.now());
    SysRole entity = roleDao.save(convert(vo, SysRole.class));
    return convert(entity, SysRoleDto.class);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public Boolean deleteById(String id) {
    // 删除用户与角色关联
    userRoleDao.deleteUserRoleByRoleId(id);
    // 删除角色与菜单操作关联
    roleMenuOperDao.deleteRoleMenuOperByRoleId(id);
    // 删除角色与菜单关联
    roleMenuDao.deleteRoleMenuByRoleId(id);
    // 删除角色
    roleDao.deleteById(id);
    return Boolean.TRUE;
  }

  @Override
  public Boolean checkRoleName(String id, String roleName) {
    try {
      Criteria<SysRole> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(SysConstants.ROLE_NAME, DataUtils.trimToNull(roleName)));
      if (!DataUtils.isEmpty(id)) {
        criteria.add(Restrictions.ne(SysConstants.ID, id));
      }
      if (roleDao.count(criteria) > 0) {
        return Boolean.TRUE;
      }
    } catch (Exception e) {
      LOGGER.error("Error checkRoleName:", e);
    }
    return Boolean.FALSE;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public Boolean saveAuthority(String menuType, JSONArray menuIds, String roleId) {
    roleMenuDao.deleteRoleMenuByRoleId(roleId);
    menuIds.forEach(menuId -> {
      SysRoleMenu roleMenu = new SysRoleMenu();
      roleMenu.setRoleId(roleId);
      roleMenu.setMenuId(menuId.toString());
      roleMenu.setMenuTypeId(menuType);
      roleMenu.setId(roleId + menuType + menuId.toString());
      roleMenuDao.save(roleMenu);
    });
    return Boolean.TRUE;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<String> findMenuByRoleId(String menuTypeId, String roleId) {
    return roleMenuDao.findMenuListByRoleId(roleId, menuTypeId);
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<SysMenuTreeDto> findMenuByRoles(List<String> roles, String menuType) {
    List<SysMenuTreeDto> result = new ArrayList<>();
    List<SysMenuDto> list = sysMenuService.selectMenuByRoles(roles, menuType, SysConstants.DEFAULT_PARENT_ID);
    list.forEach(entity -> {
      SysMenuTreeDto sysMenuTreeDto = setMenu(entity, true);
      List<SysMenuTreeDto> childArray = new ArrayList<>();
      List<SysMenuDto> childList = sysMenuService.selectMenuByRoles(roles, menuType, entity.getId());
      childList.forEach(child -> {
        SysMenuTreeDto childJsonObject = setMenu(child, false);
        List<SysMenuTreeDto> aArray = new ArrayList<>();
        List<SysMenuDto> aList = sysMenuService.selectMenuByRoles(roles, menuType, child.getId());
        aList.forEach(c -> {
          SysMenuTreeDto j = setMenu(c, false);
          aArray.add(j);
        });
        if (!aArray.isEmpty()) {
          childJsonObject.setExpand(true);
          childJsonObject.setChildren(aArray);
        }
        childArray.add(childJsonObject);
      });
      if (!childArray.isEmpty()) {
        sysMenuTreeDto.setExpand(true);
        sysMenuTreeDto.setChildren(childArray);
      }
      result.add(sysMenuTreeDto);
    });

    return result;
  }

  private SysMenuTreeDto setMenu(SysMenuDto menu, boolean isParent) {
    SysMenuTreeDto result = new SysMenuTreeDto();
    result.setId(menu.getId());
    result.setName(StringUtils.capitalize(menu.getPath()));
    result.setPath(menu.getPath());
    result.setComponent(menu.getComponent());
    result.setParent(isParent);
    if (isParent) {
      result.setHidden(false);
      result.setAlwaysShow(true);
      result.setRedirect("noRedirect");
    }
    SysMenuIconDto meta = new SysMenuIconDto();
    meta.setTitle(menu.getMenuName());
    meta.setIcon(menu.getIcon());
    result.setMeta(meta);
    return result;
  }

  @Override
  @GeneratorCache(CACHE_NAME)
  public List<SysMenuOperDto> findMenuOperByRoles(List<String> roles, String menuId) {
    List<Map<String, String>> list = roleMenuOperDao.findMenuOperByRoles(roles, menuId);
    List<SysMenuOperDto> result = new ArrayList<>();
    list.forEach(map -> {
      SysMenuOperDto entity = new SysMenuOperDto();
      entity.setOperId(map.get("operId"));
      entity.setOperName(map.get("operName"));
      entity.setOperType(map.get("operType"));
      result.add(entity);
    });
    return result;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public Boolean editStatus(String id, StatusEnum status) {
    Optional<SysRole> optional = roleDao.findById(id);
    if (optional.isPresent()) {
      SysRole entity = optional.get();
      entity.setStatus(status);
      roleDao.save(entity);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

}
