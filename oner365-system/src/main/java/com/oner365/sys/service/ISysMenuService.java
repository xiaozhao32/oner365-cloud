package com.oner365.sys.service;

import java.util.List;

import com.oner365.sys.entity.SysMenu;
import com.oner365.sys.entity.TreeSelect;

/**
 * 菜单接口
 * @author zhaoyong
 */
public interface ISysMenuService {

    /**
     * 查询详情
     *
     * @param id 主键
     * @return SysMenu
     */
    SysMenu getById(String id);

    /**
     * 保存
     *
     * @param sysMenu 对象
     * @return SysMenu
     */
    SysMenu save(SysMenu sysMenu);

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
    int editStatusById(String id, String status);

    /**
     * 按菜单类型查询
     * 
     * @param typeCode 类型编号
     * @return List<SysMenu>
     */
    List<SysMenu> findMenuByTypeCode(String typeCode);

    /**
     * 按菜单类型和角色查询
     * 
     * @param roles      角色
     * @param menuTypeId 类型编号
     * @param parentId   父级id
     * @return List<SysMenu>
     */
    List<SysMenu> selectMenuByRoles(List<String> roles, String menuTypeId, String parentId);

    /**
     * 按菜单类型查询
     * 
     * @param menuTypeId 类型id
     * @param parentId   父级id
     * @return List<SysMenu>
     */
    List<SysMenu> findMenu(String menuTypeId, String parentId);

    /**
     * 下拉树结构
     * 
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildTreeSelect(List<SysMenu> menus);

    /**
     * 下拉树
     * 
     * @param menus 菜单
     * @return List<SysMenu>
     */
    List<SysMenu> buildTree(List<SysMenu> menus);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param menu 菜单
     * @return 菜单列表
     */
    List<SysMenu> selectList(SysMenu menu);

    /**
     * 根据用户查询系统菜单列表
     *
     * @param sysMenu 菜单信息
     * @return 菜单列表
     */
    List<SysMenu> selectListByUserId(SysMenu sysMenu);

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @param menuTypeId 菜单类型id
     * @return 选中菜单列表
     */
    List<Integer> selectListByRoleId(String roleId, String menuTypeId);
}
