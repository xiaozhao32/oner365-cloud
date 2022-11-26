package com.oner365.gateway.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.gateway.entity.SysLog;

/**
 * 系统日志接口
 * @author zhaoyong
 */
@Repository
public interface ISysLogDao extends JpaRepository<SysLog, String>, JpaSpecificationExecutor<SysLog> {

}
