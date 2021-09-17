package com.oner365.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.entity.SysMenuOperation;

/**
 * 菜单操作权限接口
 * @author zhaoyong
 */
public interface ISysMenuOperationService {

    /**
     * 查询列表
     * @param data 查询参数
     * @return Page
     */
    Page<SysMenuOperation> pageList(QueryCriteriaBean data);

    /**
     * 查询列表
     * @return List
     */
    List<SysMenuOperation> findList();

    /**
     * 查询菜单
     *
     * @param menuId 菜单编号
     * @return List
     */
    List<String> selectByMenuId(String menuId);

    /**
     * 查询详情
     *
     * @param id 主键
     * @return SysMenuOperation
     */
    SysMenuOperation getById(String id);

    /**
     * 保存
     *
     * @param menuOperation 对象
     * @return SysMenuOperation
     */
    SysMenuOperation save(SysMenuOperation menuOperation);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int deleteById(String id);

    /**
     * 检测是否存在
     * @param id 主键
     * @param code 类型
     * @return long
     */
    long checkCode(String id, String code);

}
