package com.oner365.swagger.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;
import com.oner365.data.commons.enums.StatusEnum;
import com.oner365.swagger.dto.GatewayFilter;
import com.oner365.swagger.dto.GatewayPredicate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Gateway的路由定义模型
 * @author zhaoyong
 */
@ApiModel(value = "路由信息")
public class GatewayRouteVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 路由的Id
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 路由断言集合配置
     */
    @ApiModelProperty(value = "断言集合")
    private List<GatewayPredicate> predicates;

    /**
     * 路由过滤器集合配置
     */
    @ApiModelProperty(value = "过滤器集合")
    private List<GatewayFilter> filters;

    /**
     * 路由规则转发的目标uri
     */
    @ApiModelProperty(value = "转发地址", required = true)
    @NotBlank(message = "{gateway.vo.route.uri.message}")
    private String uri;

    /**
     * 路由执行的顺序
     */
    @ApiModelProperty(value = "执行顺序")
    private Integer routeOrder = 0;

    /**
     * 路由状态 1：可用 0：不可用
     */
    @ApiModelProperty(value = "路由状态", required = true)
    @NotNull(message = "{gateway.vo.route.status.message}")
    private StatusEnum status;

    /**
     * 界面使用的谓词
     */
    @ApiModelProperty(value = "表达式")
    private String pattern;

    public GatewayRouteVo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GatewayPredicate> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<GatewayPredicate> predicates) {
        this.predicates = predicates;
    }

    public List<GatewayFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<GatewayFilter> filters) {
        this.filters = filters;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(Integer routeOrder) {
        this.routeOrder = routeOrder;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    
    /**
     * toString Method
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

}
