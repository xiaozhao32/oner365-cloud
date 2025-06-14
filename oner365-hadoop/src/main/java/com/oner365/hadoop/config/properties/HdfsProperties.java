package com.oner365.hadoop.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * HDFS配置类
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "hdfs")
public class HdfsProperties {

    private String path;

    private String username;

    public HdfsProperties() {
        super();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}