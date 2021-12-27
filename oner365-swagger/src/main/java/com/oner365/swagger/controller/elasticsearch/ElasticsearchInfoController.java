package com.oner365.swagger.controller.elasticsearch;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.ResponseData;
import com.oner365.controller.BaseController;
import com.oner365.swagger.client.elasticsearch.IElasticsearchInfoClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Elasticsearch - 信息
 * 
 * @author zhaoyong
 */
@RestController
@Api(tags = "Elasticsearch 信息")
@RequestMapping("/elasticsearch/info")
public class ElasticsearchInfoController extends BaseController {

  @Autowired
  private IElasticsearchInfoClient client;

  /**
   * 首页信息
   * 
   * @return ResponseData
   */
  @ApiOperation("1.首页")
  @ApiOperationSupport(order = 1)
  @GetMapping("/index")
  public ResponseData<Serializable> index() {
    return client.index();
  }
}
