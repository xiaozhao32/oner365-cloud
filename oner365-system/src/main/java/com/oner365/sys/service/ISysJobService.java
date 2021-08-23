package com.oner365.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.SysJob;

/**
 * 职位接口
 * @author zhaoyong
 */
public interface ISysJobService {

    /**
     * 查询职位列表
     *
     * @param paramJson 参数
     * @return Page
     */
    Page<SysJob> pageList(JSONObject paramJson);
    
    /**
     * 查询列表
     * @param paramJson 参数
     * @return List
     */
    List<SysJob> findList(JSONObject paramJson);

    /**
     * 查询职位详情
     *
     * @param id 编号
     * @return SysJob
     */
    SysJob getById(String id);

    /**
     * 保存职位
     *
     * @param job 职位对象
     * @return SysJob
     */
    SysJob saveJob(SysJob job);

    /**
     * 删除职位
     *
     * @param id 编号
     * @return int
     */
    int deleteById(String id);

    /**
     * 更新状态
     *
     * @param id     编号
     * @param status 状态
     * @return Integer
     */
    Integer editStatus(String id, String status);

}
