package com.oner365.elasticsearch.dto;

import java.io.Serializable;
import java.util.List;

import co.elastic.clients.elasticsearch.indices.stats.ShardRoutingState;

/**
 * Elasticsearch cluster
 *
 * @author zhaoyong
 */
public class ClusterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Index */
    private String index;

    /** Shards */
    private Integer numberOfShards;

    /** Replicas */
    private Integer numberOfReplicas;

    /** Status */
    private ShardRoutingState status;
    
    /** properties */
    private List<ClusterMappingDto> mappingList;

    /**
     * 构造方法
     */
    public ClusterDto() {
        super();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getNumberOfShards() {
        return numberOfShards;
    }

    public void setNumberOfShards(Integer numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    public Integer getNumberOfReplicas() {
        return numberOfReplicas;
    }

    public void setNumberOfReplicas(Integer numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    public ShardRoutingState getStatus() {
        return status;
    }

    public void setStatus(ShardRoutingState status) {
        this.status = status;
    }

    /**
     * @return the mappingList
     */
    public List<ClusterMappingDto> getMappingList() {
      return mappingList;
    }

    /**
     * @param mappingList the mappingList to set
     */
    public void setMappingList(List<ClusterMappingDto> mappingList) {
      this.mappingList = mappingList;
    }
}
