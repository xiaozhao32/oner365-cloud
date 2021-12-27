package com.oner365.swagger.client.elasticsearch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.common.ResponseData;
import com.oner365.common.ResponseResult;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SampleGeneDto;
import com.oner365.swagger.vo.SampleGeneVo;

/**
 * Elasticsearch服务 - 信息
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_ELASTICSEARCH, contextId = PathConstants.CONTEXT_ELASTICSEARCH_SAMPLE_GENE_ID)
public interface IElasticsearchSampleGeneClient {

  /**
   * 列表
   * 
   * @return ResponseData<PageInfo<SampleGeneDto>>
   */
  @PostMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_LIST)
  ResponseData<PageInfo<SampleGeneDto>> list(@RequestBody QueryCriteriaBean data);

  /**
   * id查询
   * 
   * @param id 主键
   * @return ResponseData<SampleGeneDto>
   */
  @GetMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_GET_ID)
  ResponseData<SampleGeneDto> get(@PathVariable("id") String id);

  /**
   * 保存
   *
   * @param sampleGeneVo 基因对象
   * @return ResponseData<ResponseResult<SampleGeneDto>>
   */
  @PutMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_SAVE)
  ResponseData<ResponseResult<SampleGeneDto>> save(@RequestBody SampleGeneVo sampleGeneVo);

  /**
   * 删除
   * 
   * @param ids 主键
   * @return ResponseData<Integer>
   */
  @DeleteMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_GENE_DELETE)
  ResponseData<Integer> delete(@RequestBody String... ids);
}