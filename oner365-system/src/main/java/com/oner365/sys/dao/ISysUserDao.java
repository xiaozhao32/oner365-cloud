package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.sys.entity.SysUser;

/**
 * 系统用户接口
 * @author zhaoyong
 */
@Repository
public interface ISysUserDao extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser> {

}
