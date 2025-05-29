package com.oner365.hadoop.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Hive 配置类
 *
 * @author zhaoyong
 */
@ConfigurationProperties(prefix = "spring.datasource.hive")
public class HiveProperties {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    public HiveProperties() {
        super();
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}