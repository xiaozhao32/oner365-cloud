package com.oner365.sys.service;

import java.util.List;

import com.oner365.common.enums.StatusEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.common.service.BaseService;
import com.oner365.sys.dto.SysMenuDto;
import com.oner365.sys.dto.TreeSelect;
import com.oner365.sys.vo.SysMenuVo;

/**
 * 菜单接口
 * 
 * @author zhaoyong
 */
public interface ISysMenuService extends BaseService {

  /**
   * 查询详情
   *
   * @param id 主键
   * @return SysMenuDto
   */
  SysMenuDto getById(String id);

  /**
   * 保存
   *
   * @param sysMenu 对象
   * @return SysMenuDto
   */
  SysMenuDto save(SysMenuVo sysMenu);

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
   * @param id     主键
   * @param status 状态
   * @return int
   */
  int editStatus(String id, StatusEnum status);

  /**
   * 按菜单类型查询
   * 
   * @param typeCode 类型编号
   * @return List<SysMenuDto>
   */
  List<SysMenuDto> findMenuByTypeCode(String typeCode);

  /**
   * 按菜单类型和角色查询
   * 
   * @param roles      角色
   * @param menuTypeId 类型编号
   * @param parentId   父级id
   * @return List<SysMenuDto>
   */
  List<SysMenuDto> selectMenuByRoles(List<String> roles, String menuTypeId, String parentId);

  /**
   * 按菜单类型查询
   * 
   * @param menuTypeId 类型id
   * @param parentId   父级id
   * @return List<SysMenuDto>
   */
  List<SysMenuDto> findMenu(String menuTypeId, String parentId);

  /**
   * 下拉树结构
   * 
   * @param menus 菜单列表
   * @return 下拉树结构列表
   */
  List<TreeSelect> buildTreeSelect(List<SysMenuDto> menus);

  /**
   * 下拉树
   * 
   * @param menus 菜单
   * @return List<SysMenuDto>
   */
  List<SysMenuDto> buildTree(List<SysMenuDto> menus);
  
  /**
   * 查询系统菜单列表
   *
   * @param data 查询参数
   * @return 菜单列表
   */
  List<SysMenuDto> findList(QueryCriteriaBean data);

  /**
   * 查询系统菜单列表
   *
   * @param sysMenuVo 查询对象
   * @return 菜单列表
   */
  List<SysMenuDto> selectList(SysMenuVo sysMenuVo);

  /**
   * 根据用户查询系统菜单列表
   *
   * @param sysMenu 菜单信息
   * @return 菜单列表
   */
  List<SysMenuDto> selectListByUserId(SysMenuVo sysMenu);

  /**
   * 根据角色ID查询菜单树信息
   *
   * @param roleId     角色ID
   * @param menuTypeId 菜单类型id
   * @return 选中菜单列表
   */
  List<String> selectListByRoleId(String roleId, String menuTypeId);
}
