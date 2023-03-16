package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysDictItemType;

/**
 * 菜单类型接口
 * @author zhaoyong
 */
public interface ISysDictItemTypeDao extends JpaRepository<SysDictItemType, String>,JpaSpecificationExecutor<SysDictItemType>{

}
