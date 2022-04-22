package com.oner365.swagger.client.system;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
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
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysRoleDto;
import com.oner365.swagger.vo.SysRoleVo;
import com.oner365.swagger.vo.check.CheckRoleNameVo;

/**
 * 系统服务 - 角色管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_ROLE_ID)
public interface ISystemRoleClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysRoleDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ROLE_LIST)
  ResponseData<PageInfo<SysRoleDto>> list(@RequestBody QueryCriteriaBean data);

  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysRoleDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_ROLE_GET_ID)
  ResponseData<SysRoleDto> getById(@PathVariable(value = "id") String id);

  /**
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Integer>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ROLE_STATUS)
  ResponseData<Integer> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") StatusEnum status);
  
  /**
   * 判断类别id 类别是否存在
   *
   * @param checkRoleNameVo 查询参数
   * @return ResponseData<Long>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ROLE_CHECK)
  ResponseData<Long> check(@RequestBody CheckRoleNameVo checkRoleNameVo);

  /**
   * 保存
   * 
   * @param sysRoleVo 保存对象
   * @return ResponseData<ResponseResult<SysRoleDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_ROLE_SAVE)
  ResponseData<ResponseResult<SysRoleDto>> save(@RequestBody SysRoleVo sysRoleVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<Integer>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_ROLE_DELETE)
  ResponseData<Integer> delete(@RequestBody String... ids);

  /**
   * 导出
   * @param data 查询对象
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_ROLE_EXPORT)
  ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data);
}
