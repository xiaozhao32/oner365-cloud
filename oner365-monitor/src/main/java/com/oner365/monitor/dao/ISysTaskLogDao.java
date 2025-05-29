package com.oner365.monitor.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oner365.monitor.entity.SysTaskLog;

/**
 * 任务日志接口
 *
 * @author zhaoyong
 */
public interface ISysTaskLogDao extends JpaRepository<SysTaskLog, String>, JpaSpecificationExecutor<SysTaskLog> {

    /**
     * 删除任务日志
     * @param time 时间
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from nt_sys_task_log where create_time < ?1 ", nativeQuery = true)
    void deleteTaskLogByCreateTime(String time);

}
