package com.oner365.swagger.controller.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.elasticsearch.IElasticsearchSampleGeneClient;
import com.oner365.swagger.dto.SampleGeneDto;
import com.oner365.swagger.vo.SampleGeneVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch - 信息
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "Elasticsearch 基因型信息")
@RequestMapping("/elasticsearch/sample/gene")
public class ElasticsearchSampleGeneController extends BaseController {

  @Autowired
  private IElasticsearchSampleGeneClient client;

  /**
   * 首页信息
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SampleGeneDto>>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SampleGeneDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return ResponseData<SampleGeneDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SampleGeneDto> get(@PathVariable("id") String id) {
    return client.get(id);
  }

  /**
   * 保存
   *
   * @param sampleGeneVo 基因对象
   * @return ResponseData<ResponseResult<SampleGeneDto>>
   */
  @ApiOperation("3.保存")
  @ApiOperationSupport(order = 3)
  @PutMapping("/save")
  public ResponseData<ResponseResult<SampleGeneDto>> save(@RequestBody SampleGeneVo sampleGeneVo) {
    if (sampleGeneVo == null) {
      return ResponseData.error("基因对象为空!");
    }
    return client.save(sampleGeneVo);
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return Integer
   */
  @ApiOperation("4.删除")
  @ApiOperationSupport(order = 4)
  @DeleteMapping("/delete")
  public ResponseData<Integer> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
}
