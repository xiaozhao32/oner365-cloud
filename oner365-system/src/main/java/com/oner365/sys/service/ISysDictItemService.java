package com.oner365.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.SysDictItem;

/**
 * 字典接口
 *
 * @author zhaoyong
 */
public interface ISysDictItemService {

    /**
     * 添加
     *
     * @param item 对象
     * @return SysDictItem
     */
    SysDictItem save(SysDictItem item);

    /**
     * 按编号查询详情
     *
     * @param id 主键
     * @return SysDictItem
     */
    SysDictItem getById(String id);

    /**
     * 查询分页列表
     *
     * @param paramJson 查询参数
     * @return Page
     */
    Page<SysDictItem> pageList(JSONObject paramJson);

    /**
     * 查询列表
     *
     * @param paramJson 参数
     * @return List
     */
    List<SysDictItem> findList(JSONObject paramJson);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int deleteById(String id);

    /**
     * 修改状态
     *
     * @param id 主键
     * @param status 状态
     * @return int
     */
    Integer editStatus(String id, String status);

}
