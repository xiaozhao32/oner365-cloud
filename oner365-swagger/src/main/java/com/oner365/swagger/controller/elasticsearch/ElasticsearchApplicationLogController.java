package com.oner365.swagger.controller.elasticsearch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.elasticsearch.IElasticsearchApplicationLogClient;
import com.oner365.swagger.dto.ApplicationLogDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch - 应用日志
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "Elasticsearch 应用日志")
@RequestMapping("/elasticsearch/application/log")
public class ElasticsearchApplicationLogController extends BaseController {

  @Autowired
  private IElasticsearchApplicationLogClient client;

  /**
   * 获取列表
   * 
   * @param data 查询参数
   * @return ResponseData<PageInfo<ApplicationLogDto>>
   */
  @ApiOperation("1.获取列表")
  @ApiOperationSupport(order = 1)
  @PostMapping("/list")
  public ResponseData<PageInfo<ApplicationLogDto>> list(@RequestBody QueryCriteriaBean data) {
    return client.list(data);
  }

  /**
   * id查询
   *
   * @param id 编号
   * @return ResponseData<ApplicationLogDto>
   */
  @ApiOperation("2.按id查询")
  @ApiOperationSupport(order = 2)
  @GetMapping("/get/{id}")
  public ResponseData<ApplicationLogDto> get(@PathVariable("id") String id) {
    return client.get(id);
  }

  /**
   * 删除
   *
   * @param ids 编号
   * @return Integer
   */
  @ApiOperation("3.删除")
  @ApiOperationSupport(order = 4)
  @DeleteMapping("/delete")
  public ResponseData<List<Boolean>> delete(@RequestBody String... ids) {
    return client.delete(ids);
  }
}
