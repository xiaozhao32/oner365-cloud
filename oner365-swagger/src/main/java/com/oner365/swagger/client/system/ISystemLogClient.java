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

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysLogDto;
import com.oner365.swagger.vo.SysLogVo;

/**
 * 系统服务 - 日志管理
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_LOG_ID)
public interface ISystemLogClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysLogDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_LOG_LIST)
  ResponseData<PageInfo<SysLogDto>> list(@RequestBody QueryCriteriaBean data);
  
  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysLogDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_LOG_GET_ID)
  ResponseData<SysLogDto> getById(@PathVariable String id);

  /**
   * 保存
   * 
   * @param sysLogVo 保存对象
   * @return ResponseData<Boolean>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_LOG_SAVE)
  ResponseData<Boolean> save(@RequestBody SysLogVo sysLogVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_LOG_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);
  
  /**
   * 按日期删除
   * 
   * @param days 编号
   * @return ResponseData<Boolean>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_LOG_DAYS_DELETE)
  ResponseData<Boolean> deleteDays(@RequestParam Integer days);
  
  /**
   * 导出
   * @param data 查询对象
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_LOG_EXPORT)
  ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data);
}
