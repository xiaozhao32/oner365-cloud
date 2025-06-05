package com.oner365.swagger.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 缓存信息
 *
 * @author zhaoyong
 */
@ApiModel(value = "缓存命令信息")
public class CacheCommandStatsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 名称 */
    @ApiModelProperty(value = "名称")
    private String name;

    /** 值 */
    @ApiModelProperty(value = "值")
    private String value;

    public CacheCommandStatsDto() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
