package com.oner365.swagger.client.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysMenuDto;
import com.oner365.swagger.dto.SysMenuInfoDto;
import com.oner365.swagger.dto.SysMenuTreeSelectDto;
import com.oner365.swagger.dto.TreeSelect;
import com.oner365.swagger.vo.SysMenuVo;

/**
 * 系统服务 - 菜单管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_MENU_ID)
public interface ISystemMenuClient {

  /**
   * 列表
   * 
   * @param sysMenuVo 查询参数
   * @return ResponseData<SysMenuDto>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_LIST)
  ResponseData<SysMenuDto> list(@RequestBody SysMenuVo sysMenuVo);
  
  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysMenuInfoDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_MENU_GET_ID)
  ResponseData<SysMenuInfoDto> getById(@PathVariable(value = "id") String id);
  
  /**
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_STATUS)
  ResponseData<Boolean> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") StatusEnum status);
  
  /**
   * 获取菜单下拉树列表
   *
   * @param sysMenuVo 菜单对象
   * @return ResponseData<List<TreeSelect>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_TREE)
  ResponseData<List<TreeSelect>> treeselect(@RequestBody SysMenuVo sysMenuVo);
  
  /**
   * 加载对应角色菜单列表树
   *
   * @param sysMenuVo 菜单对象
   * @param roleId    String
   * @return ResponseData<SysMenuTreeSelectDto>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_ROLE)
  ResponseData<SysMenuTreeSelectDto> roleMenuTreeselect(@RequestBody SysMenuVo sysMenuVo, @PathVariable("roleId") String roleId);

  /**
   * 保存
   * 
   * @param sysMenuVo 保存对象
   * @return ResponseData<ResponseResult<SysMenuDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_MENU_SAVE)
  ResponseData<ResponseResult<SysMenuDto>> save(@RequestBody SysMenuVo sysMenuVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_MENU_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);
}
