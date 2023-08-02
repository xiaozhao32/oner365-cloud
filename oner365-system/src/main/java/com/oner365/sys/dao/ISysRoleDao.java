package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.oner365.sys.entity.SysRole;

/**
 * 系统角色接口
 *
 * @author zhaoyong
 */
public interface ISysRoleDao extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole> {

    /**
     * 查询数量
     *
     * @param id   主键
     * @param code 角色编号
     * @return int
     */
    @Query(value = "select count(id) as countRow from nt_sys_role where id<>?1 and role_code=?2", nativeQuery = true)
    int countCodeById(String id, String code);
}
