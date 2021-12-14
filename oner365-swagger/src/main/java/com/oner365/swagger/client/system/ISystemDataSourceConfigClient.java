package com.oner365.swagger.client.system;

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
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.DataSourceConfigDto;
import com.oner365.swagger.vo.DataSourceConfigVo;

/**
 * 系统服务 - 数据源配置
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_SYSTEM, contextId = PathConstants.CONTEXT_SYSTEM_DATASOURCE_CONFIG_ID)
public interface ISystemDataSourceConfigClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<DataSourceConfigDto>>
   */
  @PostMapping("/datasource/list")
  ResponseData<PageInfo<DataSourceConfigDto>> pageList(@RequestBody QueryCriteriaBean data);

  /**
   * 按id获取信息
   * 
   * @param id 编号
   * @return ResponseData<DataSourceConfigDto>
   */
  @GetMapping("/datasource/get/{id}")
  ResponseData<DataSourceConfigDto> getById(@PathVariable(value = "id") String id);

  /**
   * 按 connectName 获取信息
   * 
   * @param connectName 连接名称
   * @return ResponseData<DataSourceConfigDto>
   */
  @GetMapping("/datasource/getConnectName")
  ResponseData<DataSourceConfigDto> getConnectName(@RequestParam(value = "connectName") String connectName);

  /**
   * 保存
   * 
   * @param dataSourceConfigVo 数据源对象
   * @return ResponseData<ResponseResult<DataSourceConfigDto>>
   */
  @PutMapping("/datasource/save")
  ResponseData<ResponseResult<DataSourceConfigDto>> save(@RequestBody DataSourceConfigVo dataSourceConfigVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<Integer>
   */
  @DeleteMapping("/datasource/delete")
  ResponseData<Integer> deleteById(@RequestBody String... ids);
}
