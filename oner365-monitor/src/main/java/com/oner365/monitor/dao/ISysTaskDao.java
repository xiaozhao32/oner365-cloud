package com.oner365.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.monitor.entity.SysTask;

/**
 * 定时任务接口
 * @author zhaoyong
 */
@Repository
public interface ISysTaskDao extends JpaRepository<SysTask, String>,JpaSpecificationExecutor<SysTask>{
    
}
