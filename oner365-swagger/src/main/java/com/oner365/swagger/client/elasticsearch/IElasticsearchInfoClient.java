package com.oner365.swagger.client.elasticsearch;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.oner365.common.ResponseData;
import com.oner365.swagger.constants.PathConstants;
import com.oner365.swagger.dto.TransportClientDto;

/**
 * Elasticsearch服务 - 信息
 * 
 * @author zhaoyong
 *
 */
@FeignClient(value = PathConstants.FEIGN_CLIENT_ELASTICSEARCH, contextId = PathConstants.CONTEXT_ELASTICSEARCH_INFO_ID)
public interface IElasticsearchInfoClient {

  /**
   * 缓存信息
   * 
   * @return ResponseData<TransportClientDto>
   */
  @GetMapping(PathConstants.REQUEST_ELASTICSEARCH_INFO_INDEX)
  ResponseData<TransportClientDto> index();
}
