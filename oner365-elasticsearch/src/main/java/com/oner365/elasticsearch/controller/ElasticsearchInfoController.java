package com.oner365.elasticsearch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.controller.BaseController;
import com.oner365.elasticsearch.config.properties.ElasticsearchProperties;
import com.oner365.elasticsearch.dto.ClusterDto;
import com.oner365.elasticsearch.dto.TransportClientDto;

/**
 * Elasticsearch 信息
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/info")
public class ElasticsearchInfoController extends BaseController {

  @Autowired
  private ElasticsearchProperties elasticsearchProperties;

  /**
   * Elasticsearch 信息
   *
   * @return TransportClientDto
   */
  @GetMapping("/index")
  public TransportClientDto index() {
    // 创建客户端
    String uri = StringUtils.substringAfter(elasticsearchProperties.getUris(), "http://");
    ClientConfiguration configuration = ClientConfiguration.builder().connectedTo(uri).build();
    try (RestHighLevelClient client = RestClients.create(configuration).rest()) {
      ClusterHealthResponse healthResponse = client.cluster().health(new ClusterHealthRequest(),
          RequestOptions.DEFAULT);

      TransportClientDto result = new TransportClientDto();
      result.setHostname(StringUtils.substringBefore(uri, ":"));
      result.setPort(Integer.parseInt(StringUtils.substringAfter(uri, ":")));
      result.setClusterName(healthResponse.getClusterName());
      result.setNumberOfDataNodes(healthResponse.getNumberOfDataNodes());
      result.setActiveShards(healthResponse.getActiveShards());
      result.setStatus(healthResponse.getStatus().name());
      result.setTaskMaxWaitingTime(healthResponse.getTaskMaxWaitingTime().getMillis());

      // 索引信息
      List<ClusterDto> clusterList = new ArrayList<>();
      GetAliasesResponse aliasResponse = client.indices().getAlias(new GetAliasesRequest(), RequestOptions.DEFAULT);
      Map<String, Set<AliasMetadata>> aliasMap = aliasResponse.getAliases();
      for (Entry<String, Set<AliasMetadata>> entry : aliasMap.entrySet()) {
        SearchResponse search = client.search(new SearchRequest(entry.getKey()), RequestOptions.DEFAULT);

        ClusterDto clusterDto = new ClusterDto();
        clusterDto.setIndex(entry.getKey());
        clusterDto.setNumberOfShards(search.getTotalShards());
        clusterDto.setNumberOfReplicas(search.getNumReducePhases());
        clusterDto.setStatus(search.status().name());
        clusterList.add(clusterDto);
      }
      result.setClusterList(clusterList);
      return result;
    } catch (Exception e) {
      logger.error("index error:", e);
    }
    return null;
  }
}
