package com.oner365.sys.controller.system;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.auth.AuthUser;
import com.oner365.common.auth.annotation.CurrentUser;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.constants.SysConstants;
import com.oner365.sys.dto.SysMenuDto;
import com.oner365.sys.dto.SysMenuInfoDto;
import com.oner365.sys.dto.SysMenuTreeSelectDto;
import com.oner365.sys.dto.TreeSelect;
import com.oner365.sys.service.ISysMenuOperationService;
import com.oner365.sys.service.ISysMenuService;
import com.oner365.sys.vo.SysMenuVo;

/**
 * 系统菜单
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/menus")
public class SysMenuController extends BaseController {

  @Autowired
  public ISysMenuService menuService;

  @Autowired
  public ISysMenuOperationService operationService;

  /**
   * 获取列表
   *
   * @param data 查询对象
   * @return List<SysMenuDto>
   */
  @PostMapping("/list")
  public List<SysMenuDto> list(@RequestBody QueryCriteriaBean data) {
    return menuService.findList(data);
  }

  /**
   * 获取菜单
   *
   * @param id 编号
   * @return SysMenuInfoDto
   */
  @GetMapping("/get/{id}")
  public SysMenuInfoDto get(@PathVariable String id) {
    SysMenuInfoDto result = new SysMenuInfoDto();
    result.setSysMenu(menuService.getById(id));
    result.setMenuOperList(operationService.selectByMenuId(id));
    result.setOperationList(operationService.findList());
    return result;
  }

  /**
   * 修改菜单状态
   *
   * @param id     主键
   * @param status 状态
   * @return Boolean
   */
  @PostMapping("/status/{id}")
  public Boolean editStatus(@PathVariable String id, @RequestParam("status") StatusEnum status) {
    return menuService.editStatus(id, status);
  }

  /**
   * 获取菜单下拉树列表
   *
   * @param authUser  登录对象
   * @param sysMenuVo 菜单对象
   * @return List<TreeSelect>
   */
  @PostMapping("/tree")
  public List<TreeSelect> treeSelect(@RequestBody SysMenuVo sysMenuVo, @CurrentUser AuthUser authUser) {
    List<SysMenuDto> menus;
    if (SysConstants.DEFAULT_ROLE.equals(authUser.getIsAdmin())) {
      menus = menuService.selectList(sysMenuVo);
    } else {
      sysMenuVo.setUserId(authUser.getId());
      menus = menuService.selectListByUserId(sysMenuVo);
    }
    return menuService.buildTreeSelect(menus);
  }

  /**
   * 加载对应角色菜单列表树
   *
   * @param authUser  登录对象
   * @param sysMenuVo 菜单对象
   * @param roleId    String
   * @return SysMenuTreeSelectDto
   */
  @PostMapping("/role/{roleId}")
  public SysMenuTreeSelectDto roleMenuTreeSelect(@RequestBody SysMenuVo sysMenuVo, @PathVariable("roleId") String roleId,
      @CurrentUser AuthUser authUser) {
    List<SysMenuDto> menus;
    if (SysConstants.DEFAULT_ROLE.equals(authUser.getIsAdmin())) {
      menus = menuService.selectList(sysMenuVo);
    } else {
      sysMenuVo.setUserId(authUser.getId());
      menus = menuService.selectListByUserId(sysMenuVo);
    }
    SysMenuTreeSelectDto result = new SysMenuTreeSelectDto();
    result.setCheckedKeys(menuService.selectListByRoleId(roleId, sysMenuVo.getMenuTypeId()));
    result.setMenus(menuService.buildTreeSelect(menus));
    return result;
  }

  /**
   * 保存菜单
   *
   * @param sysMenuVo 菜单对象
   * @return ResponseResult<SysMenuDto>
   */
  @PutMapping("/save")
  public ResponseResult<SysMenuDto> save(@RequestBody SysMenuVo sysMenuVo) {
    if (sysMenuVo != null) {
      SysMenuDto entity = menuService.save(sysMenuVo);
      return ResponseResult.success(entity);
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除
   * 
   * @param ids 编号
   * @return List<Boolean>
   */
  @DeleteMapping("/delete")
  public List<Boolean> delete(@RequestBody String... ids) {
    return Arrays.stream(ids).map(id -> menuService.deleteById(id)).collect(Collectors.toList());
  }

}
