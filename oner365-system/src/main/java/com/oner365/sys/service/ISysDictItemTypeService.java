package com.oner365.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.entity.SysDictItemType;

/**
 * 字典类型接口
 * @author zhaoyong
 */
public interface ISysDictItemTypeService {

    /**
     * 添加
     *
     * @param entity 对象
     * @return SysDictItemType
     */
    SysDictItemType save(SysDictItemType entity);

    /**
     * 按编号查询详情
     *
     * @param id 主键
     * @return SysDictItemType
     */
    SysDictItemType getById(String id);

    /**
     * 查询分页列表
     * @param data 查询参数
     * @return Page
     */
    Page<SysDictItemType> pageList(QueryCriteriaBean data);
    
    /**
     * 查询列表
     * @param data 查询参数
     * @return List
     */
    List<SysDictItemType> findList(QueryCriteriaBean data);

    /**
     * 检测code是否存在
     * @param id 主键
     * @param code 编号
     * @return long
     */
    long checkCode(String id, String code);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int deleteById(String id);

    /**
     * 查询列表
     *
     * @param codeList 查询参数
     * @return List
     */
    List<SysDictItemType> findListByCodes(List<String> codeList);

    /**
     * 修改状态
     *
     * @param id 主键
     * @param status 状态
     * @return int
     */
    Integer editStatus(String id, String status);

}
