package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Elasticsearch cluster
 *
 * @author zhaoyong
 */
@ApiModel(value = "节点信息")
public class ClusterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Index */
    @ApiModelProperty(value = "主键")
    private String index;

    /** Shards */
    @ApiModelProperty(value = "分片数量")
    private Integer numberOfShards;

    /** Replicas */
    @ApiModelProperty(value = "克隆数量")
    private Integer numberOfReplicas;

    /** Status */
    @ApiModelProperty(value = "状态")
    private String status;
    
    /** properties */
    @ApiModelProperty(value = "节点信息")
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
