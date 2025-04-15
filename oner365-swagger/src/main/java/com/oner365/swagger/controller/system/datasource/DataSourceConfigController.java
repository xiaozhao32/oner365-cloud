package com.oner365.swagger.controller.system.datasource;

import java.util.List;

import javax.annotation.Resource;

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
import com.oner365.data.commons.enums.ErrorInfoEnum;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.client.system.ISystemDataSourceConfigClient;
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
public class DataSourceConfigController {

  @Resource
  private ISystemDataSourceConfigClient client;

  /**
   * 列表
   * 
   * @param data 查询参数
   * @return Page<DataSourceConfigDto>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<DataSourceConfigDto>> pageList(@RequestBody QueryCriteriaBean data) {
    return client.pageList(data);
  }

  /**
   * 按id获取信息
   * 
   * @param id 编号
   * @return ResponseData<DataSourceConfigDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<DataSourceConfigDto> get(@PathVariable String id) {
    return client.getById(id);
  }

  /**
   * 按 connectName 获取信息
   * 
   * @param connectName 连接名称
   * @return ResponseData<DataSourceConfigDto>
   */
  @ApiOperation("3.按连接名称查询")
  @ApiOperationSupport(order = 3)
  @GetMapping("/name")
  public ResponseData<DataSourceConfigDto> getConnectName(@RequestParam String connectName) {
    return client.getConnectName(connectName);
  }

  /**
   * 保存
   * 
   * @param dataSourceConfigVo 数据源对象
   * @return ResponseData<DataSourceConfigDto>
   */
  @ApiOperation("4.保存")
  @ApiOperationSupport(order = 4)
  @PutMapping("/save")
  public ResponseData<DataSourceConfigDto> save(@RequestBody DataSourceConfigVo dataSourceConfigVo) {
    if (dataSourceConfigVo != null) {
      return client.save(dataSourceConfigVo);
    }
    return ResponseData.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

  /**
   * 删除
   * 
   * @param ids 编号
   * @return List<Boolean>
   */
  @ApiOperation("5.删除")
  @ApiOperationSupport(order = 5)
  @DeleteMapping("/delete")
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.deleteById(ids);
  }
}
