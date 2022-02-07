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

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysMessageDto;
import com.oner365.swagger.enums.MessageTypeEnum;
import com.oner365.swagger.vo.SysMessageVo;

/**
 * 系统服务 - 消息管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_MESSAGE_ID)
public interface ISystemMessageClient {
  
  /**
   * 查询结果 有返回 true 并且更新状态
   * 
   * @param messageType 消息类型
   * @return ResponseResult<Boolean>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_MESSAGE_REFRESH)
  ResponseData<ResponseResult<Boolean>> refresh(@RequestParam("messageType") MessageTypeEnum messageType);

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysMessageDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MESSAGE_LIST)
  ResponseData<PageInfo<SysMessageDto>> list(@RequestBody QueryCriteriaBean data);
  
  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysMessageDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_MESSAGE_GET_ID)
  ResponseData<SysMessageDto> getById(@PathVariable(value = "id") String id);

  /**
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Integer>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MESSAGE_STATUS)
  ResponseData<Integer> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") String status);
  
  /**
   * 保存
   * 
   * @param sysMenuTypeVo 保存对象
   * @return ResponseData<ResponseResult<SysMessageDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_MESSAGE_SAVE)
  ResponseData<ResponseResult<SysMessageDto>> save(@RequestBody SysMessageVo sysMessageVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Integer>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_MESSAGE_DELETE)
  ResponseData<List<Integer>> delete(@RequestBody String... ids);
  
  /**
   * 导出
   * @param data 查询对象
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_MESSAGE_EXPORT)
  ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data);
}
