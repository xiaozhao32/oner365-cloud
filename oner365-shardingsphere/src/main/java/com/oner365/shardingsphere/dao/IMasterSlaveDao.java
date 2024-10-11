package com.oner365.shardingsphere.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.oner365.shardingsphere.entity.MasterSlave;

/**
 * 主从测试接口
 * 
 * @author zhaoyong
 *
 */
public interface IMasterSlaveDao extends JpaRepository<MasterSlave, String>, JpaSpecificationExecutor<MasterSlave> {

}
