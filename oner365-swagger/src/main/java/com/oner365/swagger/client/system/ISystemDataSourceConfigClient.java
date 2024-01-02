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

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
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
  @PostMapping(PathConstants.REQUEST_SYSTEM_DATASOURCE_LIST)
  ResponseData<PageInfo<DataSourceConfigDto>> pageList(@RequestBody QueryCriteriaBean data);

  /**
   * 按id获取信息
   * 
   * @param id 编号
   * @return ResponseData<DataSourceConfigDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_DATASOURCE_GET_ID)
  ResponseData<DataSourceConfigDto> getById(@PathVariable(value = "id") String id);

  /**
   * 按 connectName 获取信息
   * 
   * @param connectName 连接名称
   * @return ResponseData<DataSourceConfigDto>
   */
  @GetMapping(PathConstants.REQUEST_SYSTEM_DATASOURCE_GET_CONNECT_NAME)
  ResponseData<DataSourceConfigDto> getConnectName(@RequestParam(value = "connectName") String connectName);

  /**
   * 保存
   * 
   * @param dataSourceConfigVo 数据源对象
   * @return ResponseData<ResponseResult<DataSourceConfigDto>>
   */
  @PutMapping(PathConstants.REQUEST_SYSTEM_DATASOURCE_SAVE)
  ResponseData<ResponseResult<DataSourceConfigDto>> save(@RequestBody DataSourceConfigVo dataSourceConfigVo);

  /**
   * 删除
   * 
   * @param ids 编号
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_SYSTEM_DATASOURCE_DELETE)
  ResponseData<List<Boolean>> deleteById(@RequestBody String... ids);
}
