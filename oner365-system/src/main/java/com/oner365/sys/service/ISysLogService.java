package com.oner365.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.SysLog;

/**
 * 日志接口
 * @author zhaoyong
 */
public interface ISysLogService {

    /**
     * 查询分页列表
     * @param paramJson 参数
     * @return Page
     */
    Page<SysLog> pageList(JSONObject paramJson);

    /**
     * 查询列表
     * @param paramJson 参数
     * @return List
     */
    List<SysLog> findList(JSONObject paramJson);

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
     * @param date 日期
     * @return int
     */
    int deleteLog(Date date);

}
