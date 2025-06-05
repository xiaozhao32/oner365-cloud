package com.oner365.gateway.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 路由断言定义模型
 *
 * @author zhaoyong
 */
public class GatewayPredicate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 断言对应的Name
     */
    private String name;

    /**
     * 配置的断言规则
     */
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

    @Override
    public int hashCode() {
        return Objects.hash(args, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GatewayPredicate other = (GatewayPredicate) obj;
        return Objects.equals(args, other.args) && Objects.equals(name, other.name);
    }

}
