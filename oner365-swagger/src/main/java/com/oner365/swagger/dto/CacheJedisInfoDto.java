package com.oner365.swagger.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 缓存信息
 *
 * @author zhaoyong
 */
@ApiModel(value = "Redis 缓存信息")
public class CacheJedisInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** name */
    @ApiModelProperty(value = "名称")
    private String name;

    /** index */
    @ApiModelProperty(value = "索引")
    private Integer index;

    /** db size */
    @ApiModelProperty(value = "数量")
    private Long size;

    public CacheJedisInfoDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

}
