package com.oner365.sys.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.entity.SysLog;

/**
 * 日志接口
 * @author zhaoyong
 */
public interface ISysLogService {

    /**
     * 查询分页列表
     * @param data 查询参数
     * @return Page
     */
    Page<SysLog> pageList(QueryCriteriaBean data);

    /**
     * 查询列表
     * @param data 查询参数
     * @return List
     */
    List<SysLog> findList(QueryCriteriaBean data);

    /**
     * 查询详情
     * @param id 编号
     * @return SysLog
     */
    SysLog getById(String id);

    /**
     * 保存
     * @param sysLog 对象
     * @return SysLog
     */
    SysLog save(SysLog sysLog);

    /**
     * 删除
     * @param id 编号
     * @return int
     */
    int deleteById(String id);

    /**
     * 按日期删除
     * @param dateTime 日期
     * @return int
     */
    int deleteLog(LocalDateTime dateTime);

}
