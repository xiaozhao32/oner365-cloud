package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysMenuType;

/**
 * 菜单类型接口
 * @author zhaoyong
 */
public interface ISysMenuTypeDao extends JpaRepository<SysMenuType, String>, JpaSpecificationExecutor<SysMenuType> {

}
