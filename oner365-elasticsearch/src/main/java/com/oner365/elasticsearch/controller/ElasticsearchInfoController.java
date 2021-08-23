package com.oner365.elasticsearch.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.controller.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

    @Value("${spring.elasticsearch.rest.uris}")
    private String uris;

    /**
     * Elasticsearch 信息
     *
     * @return Map<String, Object>
     */
    @GetMapping("/index")
    public Map<String, Object> index() {
        String hostname = StringUtils.substringBefore(uris, ":");
        int port = 9300;
        // 指定集群
        Settings settings = Settings.builder().build();
        // 创建客户端
        Map<String, Object> result = Maps.newHashMap();
        try (final TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(hostname), port))) {
            ClusterHealthResponse response = client.admin().cluster().prepareHealth().get();

            result.put("hostname", hostname);
            result.put("port", port);
            result.put("clusterName", response.getClusterName());
            result.put("numberOfDataNodes", response.getNumberOfDataNodes());
            result.put("activeShards", response.getActiveShards());
            result.put("status", response.getStatus().name());
            result.put("taskMaxWaitingTime", response.getTaskMaxWaitingTime());
            // 索引信息
            List<Map<String, Object>> clusterList = Lists.newArrayList();
            for (ClusterIndexHealth health : response.getIndices().values()) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("index", health.getIndex());
                map.put("numberOfShards", health.getNumberOfShards());
                map.put("numberOfReplicas", health.getNumberOfReplicas());
                map.put("status", health.getStatus().toString());
                clusterList.add(map);
            }
            result.put("clusterList", clusterList);
        } catch (UnknownHostException e) {
            LOGGER.error("index error:", e);
        }
        return result;
    }
}
