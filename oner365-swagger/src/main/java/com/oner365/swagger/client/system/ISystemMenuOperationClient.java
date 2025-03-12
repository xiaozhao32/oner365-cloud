package com.oner365.swagger.client.system;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
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
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Boolean>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_STATUS)
  ResponseData<Boolean> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") StatusEnum status);

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
   * @return ResponseData<SysMenuOperationDto>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_SAVE)
  ResponseData<SysMenuOperationDto> save(@RequestBody SysMenuOperationVo sysMenuOperationVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);
  
  /**
   * 导出
   * @param data 查询对象
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MENU_OPERATION_EXPORT)
  ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data);
}
