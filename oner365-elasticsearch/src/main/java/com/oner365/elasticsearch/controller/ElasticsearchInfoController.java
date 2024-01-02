package com.oner365.elasticsearch.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.constants.PublicConstants;
import com.oner365.data.web.controller.BaseController;
import com.oner365.elasticsearch.dto.ClusterDto;
import com.oner365.elasticsearch.dto.ClusterMappingDto;
import com.oner365.elasticsearch.dto.TransportClientDto;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.NodeShard;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import co.elastic.clients.elasticsearch.indices.GetAliasResponse;
import co.elastic.clients.elasticsearch.indices.GetMappingResponse;
import co.elastic.clients.elasticsearch.indices.get_alias.IndexAliases;
import co.elastic.clients.elasticsearch.indices.get_mapping.IndexMappingRecord;
import co.elastic.clients.elasticsearch.indices.stats.ShardRoutingState;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

/**
 * Elasticsearch 信息
 *
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/info")
public class ElasticsearchInfoController extends BaseController {

  @Resource
  private ElasticsearchProperties elasticsearchProperties;

  /**
   * Elasticsearch 信息
   *
   * @return TransportClientDto
   */
  @GetMapping("/index")
  public TransportClientDto index() {
    if (ObjectUtils.isEmpty(elasticsearchProperties.getUris())) {
      logger.error("elasticsearchProperties is empty: {}", elasticsearchProperties);
      return null;
    }
    // 创建客户端
    String uri = StringUtils.substringAfter(elasticsearchProperties.getUris().get(0), PublicConstants.FILE_HTTP);

    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    // 设置密码 xpack.security.enabled: true
    if (elasticsearchProperties.getUsername() != null && elasticsearchProperties.getPassword() != null) {
      credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
          elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));
    }
    
    HttpClientConfigCallback httpClientConfigCallback = httpClientBuilder -> httpClientBuilder
        .setDefaultHeaders(
            Collections.singleton(new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString())))
        .setDefaultCredentialsProvider(credentialsProvider).addInterceptorLast(
            (HttpResponseInterceptor) (response, context) -> response.addHeader("X-Elastic-Product", "Elasticsearch"));

    try (RestClient restClient = RestClient.builder(HttpHost.create(elasticsearchProperties.getUris().get(0)))
        .setHttpClientConfigCallback(httpClientConfigCallback).build()) {

      ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
      ElasticsearchClient client = new ElasticsearchClient(transport);
      HealthResponse healthResponse = client.cluster().health();

      TransportClientDto result = new TransportClientDto();
      result.setHostname(StringUtils.substringBefore(uri, PublicConstants.COLON));
      result.setPort(Integer.parseInt(StringUtils.substringAfter(uri, PublicConstants.COLON)));
      result.setClusterName(healthResponse.clusterName());
      result.setNumberOfDataNodes(healthResponse.numberOfDataNodes());
      result.setActiveShards(healthResponse.activeShards());
      result.setStatus(healthResponse.status());
      result.setTaskMaxWaitingTime(healthResponse.taskMaxWaitingInQueueMillis());

      // 索引信息
      List<ClusterDto> clusterList = new ArrayList<>();
      GetAliasResponse aliasResponse = client.indices().getAlias();
      Map<String, IndexAliases> aliasMap = aliasResponse.result();

      List<List<NodeShard>> shards = client.searchShards().shards();

      Map<String, ShardRoutingState> stateMap = new HashMap<>(10);
      Map<String, Integer> shardsMap = new HashMap<>(10);
      shards.forEach(list -> {
        for (NodeShard shard : list) {
          stateMap.put(shard.index(), shard.state());
          shardsMap.merge(shard.index(), 1, Integer::sum);
        }
      });

      aliasMap.forEach((key, value) -> {

        ClusterDto clusterDto = new ClusterDto();
        clusterDto.setIndex(key);
        clusterDto.setNumberOfShards(shardsMap.get(key));
        clusterDto.setNumberOfReplicas(1);
        clusterDto.setStatus(stateMap.get(key));

        clusterList.add(clusterDto);
      });
      result.setClusterList(clusterList);

      // mapping信息
      GetMappingResponse mappingResponse = client.indices().getMapping();
      Map<String, IndexMappingRecord> mappings = mappingResponse.result();
      clusterList.forEach(cluster -> {
        IndexMappingRecord mappingRecord = mappings.get(cluster.getIndex());
        List<ClusterMappingDto> mappingList = new ArrayList<>();
        if (mappingRecord != null) {
          mappingRecord.mappings().properties().forEach((key, value) -> {
            ClusterMappingDto mapping = new ClusterMappingDto();
            mapping.setName(key);
            mapping.setType(value._get().getClass().getSimpleName());
            mappingList.add(mapping);
          });
        }
        cluster.setMappingList(mappingList);
      });
      return result;
    } catch (Exception e) {
      logger.error("index error:", e);
    }
    return null;
  }
}
