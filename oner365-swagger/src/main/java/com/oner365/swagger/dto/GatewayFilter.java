package com.oner365.swagger.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 过滤器定义模型
 *
 * @author zhaoyong
 */
@ApiModel(value = "过滤器")
public class GatewayFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Filter Name
     */
    @ApiModelProperty(value = "过滤器名称")
    private String name;

    /**
     * 对应的路由规则
     */
    @ApiModelProperty(value = "路由规则")
    private Map<String, String> args = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

}
