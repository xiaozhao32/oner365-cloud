package com.oner365.deploy.config.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;

/**
 * 本地部署
 *
 * @author zhaoyong
 *
 */
@Configuration
@ConfigurationProperties(prefix = "deploy.server")
public class ServerDeployProperties {

    /** 项目路径 */
    private Boolean deploy;

    /** 部署路径 */
    private String name;

    /** 项目名称 */
    private List<String> servers = new ArrayList<>();

    /**
     * 构造方法
     */
    public ServerDeployProperties() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeploy() {
        return deploy;
    }

    public void setDeploy(Boolean deploy) {
        this.deploy = deploy;
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
