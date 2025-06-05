package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysJob;

/**
 * 用户职位接口
 *
 * @author zhaoyong
 */
public interface ISysJobDao extends JpaRepository<SysJob, String>, JpaSpecificationExecutor<SysJob> {

}
