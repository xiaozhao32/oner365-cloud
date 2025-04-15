package com.oner365.swagger.controller.elasticsearch;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.client.elasticsearch.IElasticsearchSampleLocationClient;
import com.oner365.swagger.dto.SampleLocationDto;
import com.oner365.swagger.vo.SampleLocationVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch - 坐标信息
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "Elasticsearch 坐标信息")
@RequestMapping("/elasticsearch/sample/location")
public class ElasticsearchSampleLocationController {

  @Resource
  private IElasticsearchSampleLocationClient client;

  /**
   * 获取列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<SampleLocationDto>>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<SampleLocationDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return ResponseData<SampleLocationDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<SampleLocationDto> get(@PathVariable("id") String id) {
    return client.get(id);
  }

  /**
   * 保存
   *
   * @param sampleLocationVo 坐标对象
   * @return ResponseData<SampleLocationDto>
   */
  @ApiOperation("3.保存")
  @ApiOperationSupport(order = 3)
  @PutMapping("/save")
  public ResponseData<SampleLocationDto> save(@RequestBody SampleLocationVo sampleLocationVo) {
    if (sampleLocationVo == null) {
      return ResponseData.error("坐标对象为空!");
    }
    return client.save(sampleLocationVo);
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
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
}
