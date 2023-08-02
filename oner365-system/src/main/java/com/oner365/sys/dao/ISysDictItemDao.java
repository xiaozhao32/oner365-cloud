package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysDictItem;

/**
 * 字典接口
 * @author zhaoyong
 */
public interface ISysDictItemDao extends JpaRepository<SysDictItem, String>, JpaSpecificationExecutor<SysDictItem> {

}
