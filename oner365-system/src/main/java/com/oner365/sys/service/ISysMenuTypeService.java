package com.oner365.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.sys.entity.SysMenuType;

/**
 * 菜单类型接口
 * @author zhaoyong
 */
public interface ISysMenuTypeService {

    /**
     * 查询列表
     *
     * @param data 查询参数
     * @return Page
     */
    Page<SysMenuType> pageList(QueryCriteriaBean data);

    /**
     * 查询全部
     *
     * @param data 查询参数
     * @return List
     */
    List<SysMenuType> findList(QueryCriteriaBean data);

    /**
     * 查询详情
     *
     * @param id 编号
     * @return SysMenuType
     */
    SysMenuType getById(String id);

    /**
     * 保存
     *
     * @param menuType 菜单类型对象
     * @return SysMenuType
     */
    SysMenuType save(SysMenuType menuType);

    /**
     * 修改状态
     *
     * @param id     编号
     * @param status 状态
     * @return int
     */
    int editStatusById(String id, String status);

    /**
     * 检测code
     *
     * @param id   编号
     * @param code 编号
     * @return int
     */
    int checkCode(String id, String code);

    /**
     * 按菜单类型查询
     *
     * @param menuType 菜单类型
     * @return SysMenuType
     */
    SysMenuType getMenuTypeByTypeCode(String menuType);

    /**
     * 删除
     *
     * @param id 编号
     * @return int
     */
    int deleteById(String id);

}
