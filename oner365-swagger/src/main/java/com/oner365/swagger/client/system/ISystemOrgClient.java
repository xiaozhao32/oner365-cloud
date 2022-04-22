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

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.StatusEnum;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysMenuTreeSelectDto;
import com.oner365.swagger.dto.SysOrganizationDto;
import com.oner365.swagger.dto.TreeSelect;
import com.oner365.swagger.vo.SysOrganizationVo;
import com.oner365.swagger.vo.check.CheckOrgCodeVo;

/**
 * 系统服务 - 机构管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_ORG_ID)
public interface ISystemOrgClient {

  /**
   * 列表
   * 
   * @param sysOrganizationVo 查询参数
   * @return ResponseData<List<SysOrganizationDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ORG_LIST)
  ResponseData<List<SysOrganizationDto>> findList(@RequestBody SysOrganizationVo sysOrganizationVo);

  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysOrganizationDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_ORG_GET_ID)
  ResponseData<SysOrganizationDto> getById(@PathVariable(value = "id") String id);

  /**
   * 检测连接
   * 
   * @param id 编号
   * @return ResponseData<Boolean>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_ORG_CONNECTION_CHECK)
  ResponseData<Boolean> checkConnection(@PathVariable(value = "id") String id);

  /**
   * 按父级id查询
   * 
   * @param parentId 父级id
   * @return ResponseData<List<SysOrganizationDto>>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_ORG_PARENT)
  ResponseData<List<SysOrganizationDto>> parent(@RequestParam("parentId") String parentId);

  /**
   * 判断是否存在
   *
   * @param checkOrgCodeVo 查询参数
   * @return ResponseData<Long>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ORG_CHECK)
  ResponseData<Long> checkCode(@RequestBody CheckOrgCodeVo checkOrgCodeVo);

  /**
   * 获取菜单下拉树列表
   * 
   * @param sysOrganizationVo 机构对象
   * @return ResponseData<List<TreeSelect>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ORG_TREE)
  ResponseData<List<TreeSelect>> treeselect(@RequestBody SysOrganizationVo sysOrganizationVo);

  /**
   * 加载对应角色菜单列表树
   *
   * @param sysOrganizationVo 机构对象
   * @param userId            用户id
   * @return SysMenuTreeSelectDto
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ORG_USER)
  ResponseData<SysMenuTreeSelectDto> userTreeselect(@RequestBody SysOrganizationVo sysOrganizationVo,
      @PathVariable("userId") String userId);

  /**
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Integer>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ORG_STATUS)
  ResponseData<Integer> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") StatusEnum status);

  /**
   * 保存
   * 
   * @param sysOrganizationVo 保存对象
   * @return ResponseData<ResponseResult<SysOrganizationDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_ORG_SAVE)
  ResponseData<ResponseResult<SysOrganizationDto>> save(@RequestBody SysOrganizationVo sysOrganizationVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Integer>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_ORG_DELETE)
  ResponseData<List<Integer>> delete(@RequestBody String... ids);

}
