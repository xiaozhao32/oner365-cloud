package com.oner365.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oner365.sys.entity.SysRole;

/**
 * 角色接口
 * @author zhaoyong
 */
public interface ISysRoleService {

    /**
     * 查询列表
     *
     * @param paramJson 参数
     * @return Page
     */
    Page<SysRole> pageList(JSONObject paramJson);

    /**
     * 查询所有角色
     *
     * @param paramJson 参数
     * @return List
     */
    List<SysRole> findList(JSONObject paramJson);

    /**
     * 查询详情
     *
     * @param id 编号
     * @return SysRole
     */
    SysRole getById(String id);

    /**
     * 保存
     *
     * @param role 角色对象
     * @return SysRole
     */
    SysRole save(SysRole role);

    /**
     * 删除
     *
     * @param id 编号
     * @return int
     */
    int deleteById(String id);

    /**
     * 检测roleName
     *
     * @param id       编号
     * @param roleName 角色名称
     * @return long
     */
    long checkRoleName(String id, String roleName);

    /**
     * 修改状态
     *
     * @param id     编号
     * @param status 状态
     * @return Integer
     */
    Integer editStatus(String id, String status);

    /**
     * 根据用户角色获取菜单
     *
     * @param roles    角色
     * @param menuType 菜单类型
     * @return JSONArray
     */
    JSONArray findMenuByRoles(List<String> roles, String menuType);

    /**
     * 根据用户角色菜单获取菜单操作
     *
     * @param roles  角色
     * @param menuId 菜单id
     * @return List
     */
    List<Map<String, String>> findMenuOperByRoles(List<String> roles, String menuId);

    /**
     * 角色授权 不带操作权限
     *
     * @param menuType 菜单类型
     * @param menuIds  菜单编号
     * @param roleId   角色id
     * @return int
     */
    int saveAuthority(String menuType, JSONArray menuIds, String roleId);

    /**
     * 角色菜单权限
     *
     * @param menuType 菜单类型
     * @param roleId   角色id
     * @return List
     */
    List<String> findMenuByRoleId(String menuType, String roleId);

    /**
     * 菜单列表
     *
     * @param menuType 菜单类型
     * @return JSONArray
     */
    JSONArray findTreeList(String menuType);

}
