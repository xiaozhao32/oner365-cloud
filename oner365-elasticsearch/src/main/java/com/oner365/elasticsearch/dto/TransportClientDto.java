package com.oner365.elasticsearch.dto;

import java.io.Serializable;
import java.util.List;

import co.elastic.clients.elasticsearch._types.HealthStatus;

/**
 * Elasticsearch transport client
 *
 * @author zhaoyong
 */
public class TransportClientDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** hostname */
    private String hostname;

    /** port */
    private Integer port;

    /** clusterName */
    private String clusterName;

    /** nodes */
    private Integer numberOfDataNodes;

    /** active shards */
    private Integer activeShards;

    /** status */
    private HealthStatus status;

    /** max wait time */
    private String taskMaxWaitingTime;

    /** cluster list */
    private List<ClusterDto> clusterList;

    /**
     * 构造方法
     */
    public TransportClientDto() {
        super();
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Integer getNumberOfDataNodes() {
        return numberOfDataNodes;
    }

    public void setNumberOfDataNodes(Integer numberOfDataNodes) {
        this.numberOfDataNodes = numberOfDataNodes;
    }

    public Integer getActiveShards() {
        return activeShards;
    }

    public void setActiveShards(Integer activeShards) {
        this.activeShards = activeShards;
    }

    public HealthStatus getStatus() {
        return status;
    }

    public void setStatus(HealthStatus status) {
        this.status = status;
    }

    public String getTaskMaxWaitingTime() {
        return taskMaxWaitingTime;
    }

    public void setTaskMaxWaitingTime(String taskMaxWaitingTime) {
        this.taskMaxWaitingTime = taskMaxWaitingTime;
    }

    public List<ClusterDto> getClusterList() {
        return clusterList;
    }

    public void setClusterList(List<ClusterDto> clusterList) {
        this.clusterList = clusterList;
    }

}
