package com.oner365.swagger.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.dto.DataSourceConfigDto;
import com.oner365.swagger.vo.DataSourceConfigVo;

/**
 * 数据源配置
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = "oner365-system", contextId = "IDataSourceConfigServiceClient")
public interface IDataSourceConfigServiceClient {

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return ResponseData
   */
  @PostMapping("/datasource/list")
  ResponseData<Page<DataSourceConfigDto>> pageList(@RequestBody QueryCriteriaBean data);

  /**
   * 按id获取信息
   * 
   * @param id 编号
   * @return ResponseData
   */
  @GetMapping("/datasource/get/{id}")
  ResponseData<DataSourceConfigDto> getById(@PathVariable(value = "id") String id);

  /**
   * 按 connectName 获取信息
   * 
   * @param connectName 连接名称
   * @return ResponseData
   */
  @GetMapping("/datasource/getConnectName")
  ResponseData<DataSourceConfigDto> getConnectName(@RequestParam(value = "connectName") String connectName);

  /**
   * 保存
   * 
   * @param dataSourceConfigVo 数据源对象
   * @return ResponseResult<DataSourceConfigDto>
   */
  @PutMapping("/datasource/save")
  ResponseResult<DataSourceConfigDto> save(@RequestBody DataSourceConfigVo dataSourceConfigVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData
   */
  @DeleteMapping("/datasource/delete")
  ResponseData<Integer> deleteById(@RequestBody String... ids);
}
