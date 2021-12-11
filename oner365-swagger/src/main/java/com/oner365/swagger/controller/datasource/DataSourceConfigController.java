package com.oner365.swagger.controller.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.IDataSourceConfigServiceClient;
import com.oner365.swagger.dto.DataSourceConfigDto;
import com.oner365.swagger.vo.DataSourceConfigVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据源
 * 
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "数据源信息")
@RequestMapping("/system/datasource")
public class DataSourceConfigController extends BaseController {

  @Autowired
  private IDataSourceConfigServiceClient service;

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return Page<DataSourceConfigDto>
   */
  @ApiOperation("1.列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<Page<DataSourceConfigDto>> findList(@RequestBody QueryCriteriaBean data) {
    return service.pageList(data);
  }

  /**
   * 按id获取信息
   * 
   * @param id 编号
   * @return DataSourceConfig
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<DataSourceConfigDto> get(@PathVariable String id) {
    return service.getById(id);
  }

  /**
   * 按 connectName 获取信息
   * 
   * @param connectName 连接名称
   * @return DataSourceConfig
   */
  @ApiOperation("3.按连接名称查询")
  @ApiOperationSupport(order = 3)
  @GetMapping("/getConnectName")
  public ResponseData<DataSourceConfigDto> getConnectName(@RequestParam String connectName) {
    return service.getConnectName(connectName);
  }

  /**
   * 保存
   * 
   * @param dataSourceConfigVo 数据源对象
   * @return ResponseResult<DataSourceConfigDto>
   */
  @ApiOperation("4.保存")
  @ApiOperationSupport(order = 4)
  @PutMapping("/save")
  public ResponseData<ResponseResult<DataSourceConfigDto>> save(@RequestBody DataSourceConfigVo dataSourceConfigVo) {
    if (dataSourceConfigVo != null) {
      return service.save(dataSourceConfigVo);
    }
    return ResponseData.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除
   * 
   * @param ids 编号
   * @return Integer
   */
  @ApiOperation("5.删除")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/delete")
  public Integer delete(@RequestBody String... ids) {
    int code = ResultEnum.ERROR.getCode();
    for (String id : ids) {
      ResponseData<Integer> result = service.deleteById(id);
      code = result.getResult();
    }
    return code;
  }
}
