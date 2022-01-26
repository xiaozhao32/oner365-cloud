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
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SysJobDto;
import com.oner365.swagger.vo.SysJobVo;

/**
 * 系统服务 - 数据源配置
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_JOB_ID)
public interface ISystemJobClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SysJobDto>>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_JOB_LIST)
  ResponseData<PageInfo<SysJobDto>> list(@RequestBody QueryCriteriaBean data);

  /**
   * 按id获取查询
   * 
   * @param id 编号
   * @return ResponseData<SysJobDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_JOB_GET_ID)
  ResponseData<SysJobDto> getById(@PathVariable(value = "id") String id);

  /**
   * 修改状态
   * 
   * @param id     编号
   * @param status 状态
   * @return ResponseData<Integer>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_JOB_STATUS)
  ResponseData<Integer> editStatus(@PathVariable(value = "id") String id, @RequestParam("status") String status);

  /**
   * 保存
   * 
   * @param sysJobVo 保存对象
   * @return ResponseData<ResponseResult<SysJobDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_JOB_SAVE)
  ResponseData<ResponseResult<SysJobDto>> save(@RequestBody SysJobVo sysJobVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<Integer>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_JOB_DELETE)
  ResponseData<Integer> deleteById(@RequestBody String... ids);

  /**
   * 导出
   * @param data 查询对象
   * @return ResponseEntity<byte[]>
   */
  @PostMapping(PathConstants.REQUEST_SYSTEM_JOB_EXPORT)
  ResponseEntity<byte[]> export(@RequestBody QueryCriteriaBean data);

}
