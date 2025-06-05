package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Elasticsearch transport client
 *
 * @author zhaoyong
 */
@ApiModel(value = "Elasticsearch 信息")
public class TransportClientDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** hostname */
    @ApiModelProperty(value = "主机名称")
    private String hostname;

    /** port */
    @ApiModelProperty(value = "端口")
    private Integer port;

    /** clusterName */
    @ApiModelProperty(value = "节点名称")
    private String clusterName;

    /** nodes */
    @ApiModelProperty(value = "节点数")
    private Integer numberOfDataNodes;

    /** active shards */
    @ApiModelProperty(value = "分片数")
    private Integer activeShards;

    /** status */
    @ApiModelProperty(value = "状态")
    private String status;

    /** max wait time */
    @ApiModelProperty(value = "最大等待时间")
    private Long taskMaxWaitingTime;

    /** cluster list */
    @ApiModelProperty(value = "节点信息集合")
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTaskMaxWaitingTime() {
        return taskMaxWaitingTime;
    }

    public void setTaskMaxWaitingTime(Long taskMaxWaitingTime) {
        this.taskMaxWaitingTime = taskMaxWaitingTime;
    }

    public List<ClusterDto> getClusterList() {
        return clusterList;
    }

    public void setClusterList(List<ClusterDto> clusterList) {
        this.clusterList = clusterList;
    }

}
