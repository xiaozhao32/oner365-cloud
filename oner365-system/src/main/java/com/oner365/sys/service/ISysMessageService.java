package com.oner365.sys.service;

import java.util.List;

import com.oner365.sys.entity.SysMessage;

/**
 * 消息接口
 *
 * @author zhaoyong
 */
public interface ISysMessageService {

    /**
     * 添加
     *
     * @param sysMessage 对象
     * @return SysMessage
     */
    SysMessage save(SysMessage sysMessage);

    /**
     * 按编号查询详情
     *
     * @param id 主键
     * @return SysMessage
     */
    SysMessage getById(String id);

    /**
     * 按分类查询详情
     *
     * @param messageType 消息类型
     * @return List
     */
    List<SysMessage> findList(String messageType);

    /**
     * 删除
     *
     * @param id  主键
     * @return int
     */
    int deleteById(String id);

}
