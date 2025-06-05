package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.sys.entity.SysLog;

/**
 * 系统日志接口
 *
 * @author zhaoyong
 */
public interface ISysLogDao extends JpaRepository<SysLog, String>, JpaSpecificationExecutor<SysLog> {

}
