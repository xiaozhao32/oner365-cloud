package com.oner365.elasticsearch.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
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
@SuppressWarnings({ "deprecation", "resource" })
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
      String hostname = StringUtils.substringBefore(elasticsearchProperties.getUris(), ":");
      int port = 9300;
      // 指定集群
      Settings settings = Settings.builder().build();
      // 创建客户端
      try (final TransportClient client = new PreBuiltTransportClient(settings)
          .addTransportAddress(new TransportAddress(InetAddress.getByName(hostname), port))) {
        ClusterHealthResponse response = client.admin().cluster().prepareHealth().get();

        TransportClientDto result = new TransportClientDto();
        result.setHostname(hostname);
        result.setPort(port);
        result.setClusterName(response.getClusterName());
        result.setNumberOfDataNodes(response.getNumberOfDataNodes());
        result.setActiveShards(response.getActiveShards());
        result.setStatus(response.getStatus().name());
        result.setTaskMaxWaitingTime(response.getTaskMaxWaitingTime().getMillis());
        // 索引信息
        List<ClusterDto> clusterList = new ArrayList<>();
        response.getIndices().values().forEach(health -> {
          ClusterDto clusterDto = new ClusterDto();
          clusterDto.setIndex(health.getIndex());
          clusterDto.setNumberOfShards(health.getNumberOfShards());
          clusterDto.setNumberOfReplicas(health.getNumberOfReplicas());
          clusterDto.setStatus(health.getStatus().toString());
          clusterList.add(clusterDto);
        });
        result.setClusterList(clusterList);
        return result;
      } catch (UnknownHostException e) {
        logger.error("index error:", e);
      }
      return null;
    }
}
