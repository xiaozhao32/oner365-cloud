package com.oner365.sys.service.impl;

import java.sql.Timestamp;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.common.cache.annotation.RedisCacheAble;
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.query.QueryUtils;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysRoleDao;
import com.oner365.sys.dao.ISysRoleMenuDao;
import com.oner365.sys.dao.ISysRoleMenuOperDao;
import com.oner365.sys.dao.ISysUserRoleDao;
import com.oner365.sys.entity.SysMenu;
import com.oner365.sys.entity.SysRole;
import com.oner365.sys.entity.SysRoleMenu;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.sys.service.ISysRoleService;
import com.oner365.util.DataUtils;
import com.google.common.base.Strings;

/**
 * ISysRoleService实现类
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
    public Page<SysRole> pageList(JSONObject paramJson) {
        try {
            QueryCriteriaBean data = JSON.toJavaObject(paramJson, QueryCriteriaBean.class);
            Pageable pageable = QueryUtils.buildPageRequest(data);
            return roleDao.findAll(getCriteria(paramJson), pageable);
        } catch (Exception e) {
            LOGGER.error("Error pageList: ", e);
        }
        return null;
    }
    
    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysRole> findList(JSONObject paramJson) {
        Criteria<SysRole> criteria = getCriteria(paramJson);
        criteria.add(Restrictions.eq(SysConstants.STATUS, PublicConstants.STATUS_YES));
        return roleDao.findAll(criteria);
    }
    
    private Criteria<SysRole> getCriteria(JSONObject paramJson) {
        Criteria<SysRole> criteria = new Criteria<>();
        criteria.add(Restrictions.like(SysConstants.ROLE_NAME, paramJson.getString(SysConstants.ROLE_NAME)));
        criteria.add(Restrictions.eq(SysConstants.STATUS, paramJson.getString(SysConstants.STATUS)));
        if (paramJson.get(SysConstants.BEGIN_TIME) != null && paramJson.get(SysConstants.END_TIME) != null) {
            criteria.add(Restrictions.between(SysConstants.CREATE_TIME,
                paramJson.getString(SysConstants.BEGIN_TIME) + "|" + paramJson.getString(SysConstants.END_TIME)));
        }
        return criteria;
    }

    @Override
    @RedisCacheAble(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    public SysRole getById(String id) {
        try {
            Optional<SysRole> optional = roleDao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getInfoById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public SysRole save(SysRole role) {
        if (Strings.isNullOrEmpty(role.getId())) {
            role.setStatus(PublicConstants.STATUS_YES);
            role.setCreateTime(new Timestamp(System.currentTimeMillis()));
        }
        if (Strings.isNullOrEmpty(role.getRoleCode())) {
            role.setRoleCode(String.valueOf(System.currentTimeMillis()));
        }
        role.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return roleDao.save(role);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public int deleteById(String id) {
        // 删除用户与角色关联
        userRoleDao.deleteUserRoleByRoleId(id);
        // 删除角色与菜单操作关联
        roleMenuOperDao.deleteRoleMenuOperByRoleId(id);
        // 删除角色与菜单关联
        roleMenuDao.deleteRoleMenuByRoleId(id);
        // 删除角色
        roleDao.deleteById(id);
        return 1;
    }
    
    @Override
    public long checkRoleName(String id, String roleName) {
        try {
            Criteria<SysRole> criteria = new Criteria<>();
            criteria.add(Restrictions.eq(SysConstants.ROLE_NAME, DataUtils.trimToNull(roleName)));
            if (!Strings.isNullOrEmpty(id)) {
                criteria.add(Restrictions.ne(SysConstants.ID, id));
            }
            return roleDao.count(criteria);
        } catch (Exception e) {
            LOGGER.error("Error checkRoleName:", e);
        }
        return 0L;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
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
        return 1;
    }
    
    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<String> findMenuByRoleId(String menuTypeId, String roleId) {
        return roleMenuDao.findMenuListByRoleId(roleId, menuTypeId);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public JSONArray findTreeList(String menuType) {
        JSONArray jsonArray = new JSONArray();
        List<SysMenu> list = sysMenuService.findMenu(menuType, SysConstants.DEFAULT_PARENT_ID);
        list.forEach(entity -> {
            JSONObject jsonObject = setMenu(entity, true);
            JSONArray childArray = new JSONArray();
            List<SysMenu> childList = sysMenuService.findMenu(menuType, entity.getId());
            childList.forEach(child -> {
                JSONObject childJsonObject = setMenu(child, false);
                JSONArray aArray = new JSONArray();
                List<SysMenu> aList = sysMenuService.findMenu(menuType, child.getId());
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

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public JSONArray findMenuByRoles(List<String> roles, String menuType) {
        JSONArray jsonArray = new JSONArray();
        List<SysMenu> list = sysMenuService.selectMenuByRoles(roles, menuType, SysConstants.DEFAULT_PARENT_ID);
        list.forEach(entity -> {
            JSONObject jsonObject = setMenu(entity, true);
            JSONArray childArray = new JSONArray();
            List<SysMenu> childList = sysMenuService.selectMenuByRoles(roles, menuType, entity.getId());
            childList.forEach(child -> {
                JSONObject childJsonObject = setMenu(child, false);
                JSONArray aArray = new JSONArray();
                List<SysMenu> aList = sysMenuService.selectMenuByRoles(roles, menuType, child.getId());
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
    
    private JSONObject setMenu(SysMenu menu, boolean isParent) {
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
        json.put("meta",j);
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
        @CacheEvict(value = CACHE_MENU_NAME, allEntries = true)
    })
    public Integer editStatus(String id, String status) {
        SysRole entity = this.getById(id);
        if (entity != null && entity.getId() != null) {
            entity.setStatus(status);
            this.save(entity);
            return 1;
        }
        return 0;
    }

}
