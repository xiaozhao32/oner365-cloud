package com.oner365.sys.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.oner365.sys.dao.ISysRoleDao;
import com.oner365.sys.dao.ISysRoleMenuDao;
import com.oner365.sys.dao.ISysRoleMenuOperDao;
import com.oner365.sys.dao.ISysUserRoleDao;
import com.oner365.sys.dto.SysMenuDto;
import com.oner365.sys.dto.SysRoleDto;
import com.oner365.sys.entity.SysRole;
import com.oner365.sys.entity.SysRoleMenu;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.sys.vo.SysRoleVo;
import com.oner365.util.DataUtils;

/**
 * 系统角色接口实现类
 *
 * @author zhaoyong
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SysRoleServiceImpl.class);

  private static final String CACHE_NAME = "Auth:SysRole";
  private static final String CACHE_MENU_NAME = "SysMenu";

  @Autowired
  private ISysRoleDao roleDao;

  @Autowired
  private ISysRoleMenuDao roleMenuDao;

  @Autowired
  private ISysRoleMenuOperDao roleMenuOperDao;

  @Autowired
  private ISysUserRoleDao userRoleDao;

  @Autowired
  private ISysMenuService sysMenuService;

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public PageInfo<SysRoleDto> pageList(QueryCriteriaBean data) {
    try {
      return convertDto(roleDao.findAll(QueryUtils.buildCriteria(data), QueryUtils.buildPageRequest(data)));
    } catch (Exception e) {
      LOGGER.error("Error pageList: ", e);
    }
    return null;
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<SysRoleDto> findList(QueryCriteriaBean data) {
    try {
      return convertDto(roleDao.findAll(QueryUtils.buildCriteria(data)));
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
      return convertDto(optional.orElse(null));
    } catch (Exception e) {
      LOGGER.error("Error getInfoById: ", e);
    }
    return null;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
  @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public SysRoleDto save(SysRoleVo vo) {
    SysRole role = toPojo(vo);
    if (DataUtils.isEmpty(role.getId())) {
      role.setStatus(StatusEnum.YES.getCode());
      role.setCreateTime(LocalDateTime.now());
    }
    if (DataUtils.isEmpty(role.getRoleCode())) {
      role.setRoleCode(String.valueOf(System.currentTimeMillis()));
    }
    role.setUpdateTime(LocalDateTime.now());
    return convertDto(roleDao.save(role));
  }

  /**
   * 转换对象
   * 
   * @return SysRole
   */
  private SysRole toPojo(SysRoleVo vo) {
    SysRole result = new SysRole();
    result.setId(vo.getId());
    result.setCreateTime(vo.getCreateTime());
    result.setRoleCode(vo.getRoleCode());
    result.setRoleDes(vo.getRoleDes());
    result.setRoleName(vo.getRoleName());
    result.setStatus(vo.getStatus());
    result.setUpdateTime(vo.getUpdateTime());
    return result;
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public int deleteById(String id) {
    // 删除用户与角色关联
    userRoleDao.deleteUserRoleByRoleId(id);
    // 删除角色与菜单操作关联
    roleMenuOperDao.deleteRoleMenuOperByRoleId(id);
    // 删除角色与菜单关联
    roleMenuDao.deleteRoleMenuByRoleId(id);
    // 删除角色
    roleDao.deleteById(id);
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  public long checkRoleName(String id, String roleName) {
    try {
      Criteria<SysRole> criteria = new Criteria<>();
      criteria.add(Restrictions.eq(SysConstants.ROLE_NAME, DataUtils.trimToNull(roleName)));
      if (!DataUtils.isEmpty(id)) {
        criteria.add(Restrictions.ne(SysConstants.ID, id));
      }
      return roleDao.count(criteria);
    } catch (Exception e) {
      LOGGER.error("Error checkRoleName:", e);
    }
    return ExistsEnum.NO.getCode();
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public int saveAuthority(String menuType, JSONArray menuIds, String roleId) {
    roleMenuDao.deleteRoleMenuByRoleId(roleId);
    menuIds.forEach(menuId -> {
      SysRoleMenu roleMenu = new SysRoleMenu();
      roleMenu.setRoleId(roleId);
      roleMenu.setMenuId(menuId.toString());
      roleMenu.setMenuTypeId(menuType);
      roleMenu.setId(roleId + menuType + menuId.toString());
      roleMenuDao.save(roleMenu);
    });
    return ResultEnum.SUCCESS.getCode();
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public List<String> findMenuByRoleId(String menuTypeId, String roleId) {
    return roleMenuDao.findMenuListByRoleId(roleId, menuTypeId);
  }

  @Override
  @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
  public JSONArray findMenuByRoles(List<String> roles, String menuType) {
    JSONArray jsonArray = new JSONArray();
    List<SysMenuDto> list = sysMenuService.selectMenuByRoles(roles, menuType, SysConstants.DEFAULT_PARENT_ID);
    list.forEach(entity -> {
      JSONObject jsonObject = setMenu(entity, true);
      JSONArray childArray = new JSONArray();
      List<SysMenuDto> childList = sysMenuService.selectMenuByRoles(roles, menuType, entity.getId());
      childList.forEach(child -> {
        JSONObject childJsonObject = setMenu(child, false);
        JSONArray aArray = new JSONArray();
        List<SysMenuDto> aList = sysMenuService.selectMenuByRoles(roles, menuType, child.getId());
        aList.forEach(c -> {
          JSONObject j = setMenu(c, false);
          aArray.add(j);
        });
        if (!aArray.isEmpty()) {
          childJsonObject.put(SysConstants.EXPAND, true);
          childJsonObject.put(SysConstants.CHILDREN, aArray);
        }
        childArray.add(childJsonObject);
      });
      if (!childArray.isEmpty()) {
        jsonObject.put(SysConstants.EXPAND, true);
        jsonObject.put(SysConstants.CHILDREN, childArray);
      }
      jsonArray.add(jsonObject);
    });

    return jsonArray;
  }

  private JSONObject setMenu(SysMenuDto menu, boolean isParent) {
    JSONObject json = new JSONObject();
    json.put("id", menu.getId());
    json.put("name", StringUtils.capitalize(menu.getPath()));
    json.put("path", menu.getPath());
    json.put("component", menu.getComponent());
    if (isParent) {
      json.put("hidden", false);
      json.put("alwaysShow", true);
      json.put("redirect", "noRedirect");
    }
    JSONObject j = new JSONObject();
    j.put("title", menu.getMenuName());
    j.put("icon", menu.getIcon());
    json.put("meta", j);
    return json;
  }

  @Override
  public List<Map<String, String>> findMenuOperByRoles(List<String> roles, String menuId) {
    return roleMenuOperDao.findMenuOperByRoles(roles, menuId);
  }

  @Override
  @Transactional(rollbackFor = ProjectRuntimeException.class)
  @Caching(evict = { 
      @CacheEvict(value = CACHE_NAME, allEntries = true),
      @CacheEvict(value = CACHE_MENU_NAME, allEntries = true) })
  public Integer editStatus(String id, String status) {
    Optional<SysRole> optional = roleDao.findById(id);
    if (optional.isPresent()) {
      SysRole entity = optional.get();
      entity.setStatus(status);
      roleDao.save(entity);
      return ResultEnum.SUCCESS.getCode();
    }
    return ResultEnum.ERROR.getCode();
  }

}
