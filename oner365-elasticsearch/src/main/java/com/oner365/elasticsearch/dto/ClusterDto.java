package com.oner365.elasticsearch.dto;

import java.io.Serializable;

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
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}