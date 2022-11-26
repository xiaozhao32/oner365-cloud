package com.oner365.sys.dao;

import java.util.List;

import com.oner365.common.exception.ProjectRuntimeException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.sys.entity.SysRoleMenu;

/**
 * 角色菜单权限接口
 *
 * @author zhaoyong
 */
@Repository
public interface ISysRoleMenuDao extends JpaRepository<SysRoleMenu, String>, JpaSpecificationExecutor<SysRoleMenu> {

    /**
     * 查询列表
     *
     * @param roleId     角色编号
     * @param menuTypeId 菜单类型编号
     * @return List
     */
    @Query(value = "select menu_id from nt_sys_role_menu where role_id=?1 and menu_type_id=?2", nativeQuery = true)
    List<String> findMenuListByRoleId(String roleId, String menuTypeId);

    /**
     * 删除角色菜单权限
     *
     * @param roleId 角色编号
     */
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from nt_sys_role_menu where role_id=?1", nativeQuery = true)
    void deleteRoleMenuByRoleId(String roleId);

    /**
     * 删除角色菜单权限
     *
     * @param menuId 菜单编号
     */
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from nt_sys_role_menu where menu_id=?1", nativeQuery = true)
    void deleteByMenuId(String menuId);

}
