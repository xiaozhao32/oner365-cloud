package com.oner365.swagger.client.elasticsearch;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.jpa.page.PageInfo;
import com.oner365.data.jpa.query.QueryCriteriaBean;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.SampleLocationDto;
import com.oner365.swagger.vo.SampleLocationVo;

/**
 * Elasticsearch服务 - 坐标信息
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_ELASTICSEARCH, contextId = PathConstants.CONTEXT_ELASTICSEARCH_SAMPLE_LOCATION_ID)
public interface IElasticsearchSampleLocationClient {

  /**
   * 列表
   * 
   * @param data 查询条件
   * @return ResponseData<PageInfo<SampleLocationDto>>
   */
  @PostMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_LIST)
  ResponseData<PageInfo<SampleLocationDto>> list(@RequestBody QueryCriteriaBean data);

  /**
   * id查询
   * 
   * @param id 主键
   * @return ResponseData<SampleLocationDto>
   */
  @GetMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_GET_ID)
  ResponseData<SampleLocationDto> get(@PathVariable String id);

  /**
   * 保存
   *
   * @param sampleLocationVo 坐标对象
   * @return ResponseData<SampleLocationDto>
   */
  @PutMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_SAVE)
  ResponseData<SampleLocationDto> save(@RequestBody SampleLocationVo sampleLocationVo);

  /**
   * 删除
   * 
   * @param ids 主键
   * @return ResponseData<List<Boolean>>
   */
  @DeleteMapping(PathConstants.REQUEST_ELASTICSEARCH_SAMPLE_LOCATION_DELETE)
  ResponseData<List<Boolean>> delete(@RequestBody String... ids);
}
