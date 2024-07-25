package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysConfig;

/**
 * nt_sys_config Dao 接口
 * 
 * @author zhaoyong
 */
public interface ISysConfigDao extends JpaRepository<SysConfig, String>, JpaSpecificationExecutor<SysConfig> {
    
}