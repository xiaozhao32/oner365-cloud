package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.sys.entity.SysDictItem;

/**
 * 字典接口
 * @author zhaoyong
 */
@Repository
public interface ISysDictItemDao extends JpaRepository<SysDictItem, String>, JpaSpecificationExecutor<SysDictItem> {

}
