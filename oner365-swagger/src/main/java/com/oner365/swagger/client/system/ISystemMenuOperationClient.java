package com.oner365.swagger.client.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysMenuOperationDto;
import com.oner365.swagger.vo.SysMenuOperationVo;
import com.oner365.swagger.vo.check.CheckCodeVo;

/**
 * 系统服务 - 菜单操作管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_MENU_OPERATION_ID)
public interface ISystemMenuOperationClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysMenuOperationDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_LIST)
  ResponseData<PageInfo<SysMenuOperationDto>> list(@RequestBody QueryCriteriaBean data);
  
  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysMenuOperationDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_GET_ID)
  ResponseData<SysMenuOperationDto> getById(@PathVariable(value = "id") String id);

  /**
   * 判断是否存在
   *
   * @param checkCodeVo 查询参数
   * @return ResponseData<Long>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_CHECK)
  ResponseData<Boolean> checkCode(@RequestBody CheckCodeVo checkCodeVo);

  /**
   * 保存
   * 
   * @param sysMenuOperationVo 保存对象
   * @return ResponseData<ResponseResult<SysMenuOperationDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_SAVE)
  ResponseData<ResponseResult<SysMenuOperationDto>> save(@RequestBody SysMenuOperationVo sysMenuOperationVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);
}
