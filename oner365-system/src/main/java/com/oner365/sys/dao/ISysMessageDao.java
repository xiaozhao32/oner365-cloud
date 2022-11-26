package com.oner365.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oner365.sys.entity.SysMessage;

/**
 * 消息接口
 * @author zhaoyong
 */
@Repository
public interface ISysMessageDao extends JpaRepository<SysMessage, String>, JpaSpecificationExecutor<SysMessage> {

}
