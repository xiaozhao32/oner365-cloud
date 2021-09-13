package com.oner365.sys.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.oner365.common.cache.annotation.RedisCachePut;
import com.oner365.common.constants.PublicConstants;
import com.oner365.common.exception.ProjectRuntimeException;
import com.oner365.common.query.Criteria;
import com.oner365.common.query.Restrictions;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dao.ISysMenuDao;
import com.oner365.sys.dao.ISysMenuOperDao;
import com.oner365.sys.dao.ISysRoleMenuDao;
import com.oner365.sys.entity.SysMenu;
import com.oner365.sys.entity.SysMenuOper;
import com.oner365.sys.entity.SysMenuOperation;
import com.oner365.sys.entity.TreeSelect;
import com.oner365.sys.mapper.SysMenuMapper;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.util.DataUtils;
import com.google.common.collect.Lists;

/**
 * ISysMenuService实现类
 * @author zhaoyong
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    private static final String CACHE_NAME = "SysMenu";
    private static final String CACHE_ROLE_NAME = "Auth:SysRole";

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
    public SysMenu getById(String id) {
        try {
            Optional<SysMenu> optional = menuDao.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            LOGGER.error("Error getById: ", e);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @RedisCachePut(value = CACHE_NAME, key = PublicConstants.KEY_ID)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_ROLE_NAME, allEntries = true)
    })
    public SysMenu save(SysMenu menu) {
        menu.setStatus(PublicConstants.STATUS_YES);
        menu.setCreateTime(new Timestamp(System.currentTimeMillis()));
        menu.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        menuDao.save(menu);

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
        return menu;
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_ROLE_NAME, allEntries = true)
    })
    public int editStatusById(String id, String status) {
        SysMenu entity = getById(id);
        if (entity != null && entity.getId() != null) {
            entity.setStatus(status);
            menuDao.save(entity);
            return 1;
        }
        return 0;
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysMenu> findMenuByTypeCode(String typeCode) {
        try {
            return menuDao.findMenuByTypeCode(typeCode);
        } catch (Exception e) {
            LOGGER.error("Error findMenuByTypeCode: ", e);
        }
        return Lists.newArrayList();
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysMenu> selectMenuByRoles(List<String> roles, String menuTypeId, String parentId) {
        try {
            return menuMapper.selectMenuByRoles(roles, menuTypeId, parentId);
        } catch (Exception e) {
            LOGGER.error("Error findMenuByRoles: ", e);
        }
        return Lists.newArrayList();
    }
    
    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysMenu> findMenu(String menuTypeId, String parentId) {
        Criteria<SysMenu> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("menuTypeId", menuTypeId));
        criteria.add(Restrictions.eq("parentId", parentId));
        criteria.add(Restrictions.eq(SysConstants.STATUS, PublicConstants.STATUS_YES));
        return menuDao.findAll(criteria);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<TreeSelect> buildTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<SysMenu> buildTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu t : menus) {
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
     * @param t 菜单
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        if (!childList.isEmpty()) {
            t.setChildren(childList);
            for (SysMenu tChild : childList) {
                if (hasChild(list, tChild)) {
                    // 判断是否有子节点
                    for (SysMenu n : childList) {
                        recursionFn(list, n);
                    }
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> result = new ArrayList<>();
        for (SysMenu n : list) {
            if (n.getParentId().equals(t.getId())) {
                result.add(n);
            }
        }
        return result;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return !getChildList(list, t).isEmpty();
    }

    @Override
    public List<SysMenu> selectList(SysMenu menu) {
        return menuMapper.selectList(menu);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<SysMenu> selectListByUserId(SysMenu sysMenu) {
        return menuMapper.selectListByUserId(sysMenu);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = PublicConstants.KEY_GENERATOR)
    public List<Integer> selectListByRoleId(String roleId, String menuTypeId) {
        return menuMapper.selectListByRoleId(roleId, menuTypeId);
    }

    @Override
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Caching(evict = {
        @CacheEvict(value = CACHE_NAME, allEntries = true),
        @CacheEvict(value = CACHE_ROLE_NAME, allEntries = true)
    })
    public int deleteById(String id) {
        roleMenuDao.deleteByMenuId(id);
        menuDao.deleteById(id);
        return 1;
    }

}
