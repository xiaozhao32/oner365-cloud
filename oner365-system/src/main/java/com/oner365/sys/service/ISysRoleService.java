package com.oner365.sys.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysMenuOperDto;
import com.oner365.sys.dto.SysMenuTreeDto;
import com.oner365.sys.dto.SysRoleDto;
import com.oner365.sys.vo.SysRoleVo;

/**
 * 角色接口
 * 
 * @author zhaoyong
 */
public interface ISysRoleService extends BaseService {

  /**
   * 查询列表
   * 
   * @param data 查询参数
   * @return PageInfo<SysRoleDto>
   */
  PageInfo<SysRoleDto> pageList(QueryCriteriaBean data);

  /**
   * 查询所有角色
   * 
   * @param data 查询参数
   * @return List<SysRoleDto>
   */
  List<SysRoleDto> findList(QueryCriteriaBean data);

  /**
   * 查询详情
   * 
   * @param id 编号
   * @return SysRoleDto
   */
  SysRoleDto getById(String id);

  /**
   * 保存
   * 
   * @param role 角色对象
   * @return SysRoleDto
   */
  SysRoleDto save(SysRoleVo role);

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
   * @return List<SysMenuTreeDto>
   */
  List<SysMenuTreeDto> findMenuByRoles(List<String> roles, String menuType);

  /**
   * 根据用户角色菜单获取菜单操作
   * 
   * @param roles  角色
   * @param menuId 菜单id
   * @return List<SysMenuOperDto>
   */
  List<SysMenuOperDto> findMenuOperByRoles(List<String> roles, String menuId);

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

}
