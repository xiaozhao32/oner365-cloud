package com.oner365.sys.dao;

import java.util.List;

import com.oner365.common.exception.ProjectRuntimeException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.sys.entity.SysUserRole;

/**
 * 用户角色权限接口
 * @author zhaoyong
 */
@Repository
public interface ISysUserRoleDao extends JpaRepository<SysUserRole, String>,JpaSpecificationExecutor<SysUserRole>{

    /**
     * 查询用户角色权限列表
     * @param userId 用户编号
     * @return List
     */
    @Query(value = "select r.id from nt_sys_user_role ur, nt_sys_role r where ur.role_id=r.id and user_id=?1",nativeQuery = true)
    List<String> findUserRoleByUserId(String userId);

    /**
     * 删除用户角色权限
     * @param userId 用户编号
     */
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from nt_sys_user_role where user_id=?1 ",nativeQuery = true)
    void deleteUserRoleByUserId(String userId);

    /**
     * 删除用户角色权限
     * @param roleId 角色编号
     */
    @Transactional(rollbackFor = ProjectRuntimeException.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from nt_sys_user_role where role_id=?1 ",nativeQuery = true)
    void deleteUserRoleByRoleId(String roleId);

}
